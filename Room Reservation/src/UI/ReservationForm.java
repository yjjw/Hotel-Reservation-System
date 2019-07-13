package UI;

import Oracle.*;
import Object.*;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class ReservationForm extends JDialog {
    private OraVIP vm = new OraVIP();
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JPanel infoPanel;
    private JLabel rmnumtxt;
    private JLabel guestnumtxt;
    private JLabel chkouttxt;
    private JLabel chkintxt;
    private JLabel roomNumberLabel;
    private JComboBox inYear;
    private JComboBox inMonth;
    private JComboBox inDate;
    private JCheckBox mealCheckBox;
    private JComboBox outYear;
    private JComboBox outMonth;
    private JComboBox outDate;
    private JCheckBox parkingCheckBox;
    private JTextField plateField;
    private JLabel priceLable;
    private JLabel roomTypeLabel;
    private JComboBox mealComboBox;
    private JCheckBox showOnlyPopularMealCheckBox;
    private JSpinner guestSpinner;
    private JSlider slider1;
    private JCheckBox usePointsCheckBox;
    private JButton addButton;
    private JLabel sliderValue;
    private static ReservationForm dialog;
    private OraRoom rm = new OraRoom();
    private OraGuest gm = new OraGuest();
    private OraIncludes_Meal mm = new OraIncludes_Meal();
    private RoomInfo room;
    private List<String> mealSelection;
    private List<String> selectedMeal = new ArrayList<String>();
    private GuestInfo guest;
    private OraMakeReservation resm = new OraMakeReservation();
    private OraParking_Space parkm = new OraParking_Space();
    private OraProvides stallm =  new OraProvides();
    private OraBooked_At bm = new OraBooked_At();


    public ReservationForm(int roomNum,int id) {
        guest = gm.getGuestById(id);

        room = rm.getRoomByRoomNum(roomNum);
        roomNumberLabel.setText(""+room.getRoom_num());
        roomTypeLabel.setText(room.getType());
        priceLable.setText("$"+room.getPrice());
        if(vm.getVipWithID(id) == null){
            usePointsCheckBox.setEnabled(false);
            slider1.setEnabled(false);
        }else{
            usePointsCheckBox.setEnabled(true);
            usePointsCheckBox.setSelected(false);
            slider1.setEnabled(false);
            slider1.setMaximum((int)Math.min(vm.getVipPoints(id)/10,room.getPrice()));
        }
        mealComboBox.setEnabled(false);
        mealCheckBox.setSelected(false);
        showOnlyPopularMealCheckBox.setSelected(false);
        showOnlyPopularMealCheckBox.setEnabled(false);
        parkingCheckBox.setSelected(false);
        plateField.setEnabled(false);

        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        showOnlyPopularMealCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(showOnlyPopularMealCheckBox.isSelected()){
                    mealSelection = mm.getPopularMeals();
                }else{
                    mealSelection = mm.allMeal();
                }
                mealComboBox.setModel(new DefaultComboBoxModel(mealSelection.toArray()));
            }
        });
        mealCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(mealCheckBox.isSelected()){
                    mealComboBox.setEnabled(true);
                    showOnlyPopularMealCheckBox.setEnabled(true);
                }else {
                    mealComboBox.setEnabled(false);
                    showOnlyPopularMealCheckBox.setEnabled(false);
                }

                if(showOnlyPopularMealCheckBox.isSelected()){
                    mealSelection = mm.getPopularMeals();

                }else{
                    mealSelection = mm.allMeal();

                }
                mealComboBox.setModel(new DefaultComboBoxModel(mealSelection.toArray()));
            }
        });
        parkingCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(parkingCheckBox.isSelected()){
                    plateField.setEnabled(true);
                }else {
                    plateField.setEnabled(false);
                }
            }
        });
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(dialog, mealComboBox.getSelectedItem().toString());
                selectedMeal.add(mealComboBox.getSelectedItem().toString());
            }
        });
        usePointsCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(usePointsCheckBox.isSelected()){
                    slider1.setEnabled(true);
                }
            }
        });
        slider1.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                sliderValue.setText(""+slider1.getValue());
            }
        });
    }

    private void onOK() {

        int gn = (Integer)guestSpinner.getValue();
        Date in = new Date(inYear.getSelectedIndex()+2018-1900,inMonth.getSelectedIndex(),inDate.getSelectedIndex()+1);
        Date out = new Date(outYear.getSelectedIndex()+2018-1900,outMonth.getSelectedIndex(),outDate.getSelectedIndex()+1);
        if(in.after(out)){
            JOptionPane.showMessageDialog(dialog, "check in date must earlier than check out date");
            return;
        }
        int gid = guest.getID();
        double discount = 0;
        if(usePointsCheckBox.isSelected()){
            discount = slider1.getValue();
            vm.updatePoints(gid,discount);
        }

        int resNum = resm.InsertReservation(gn,in,out,discount,gid);
        if(resNum == -1){
            JOptionPane.showMessageDialog(dialog, "Adding Reservation failed");
            return;
        }
        JOptionPane.showMessageDialog(dialog, "Reservation"+resNum+" created");

        bm.InsertBook_At(room.getRoom_num(),resNum);


        //parking
        if(parkingCheckBox.isSelected()){
            String plate = plateField.getText();
            //add it to parking
            stallm.addProvidesInfo(resNum,parkm.InsertParking(plate));
        }

        if(mealCheckBox.isSelected()||!selectedMeal.isEmpty()){
            mm.InsertMeal(resNum,selectedMeal);
        }

        dialog.dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dialog.dispose();
    }

    public static void run(int rmNum,int id) {
        dialog = new ReservationForm(rmNum,id);
        dialog.pack();
        dialog.setVisible(true);
    }
}
