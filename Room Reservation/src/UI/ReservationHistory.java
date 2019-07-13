package UI;

import Oracle.*;
import Object.*;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.*;
import java.util.List;


public class ReservationHistory extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JList ResList;
    private JLabel rlTitle;
    private JPanel infoPanel;
    private JLabel checkInLabel;
    private JLabel checkOutLabel;
    private JLabel guestNumberLabel;
    private JLabel roomTypeLabel;
    private JLabel discountLabel;
    private JLabel mealLabel;
    private JLabel roomNumberLabel;
    private JLabel parkingLabel;
    private JPanel mainPanel;
    private static ReservationHistory dialog;
    GuestInfo guest;

    OraGuest gm = new OraGuest();
    OraMakeReservation resm=new OraMakeReservation();
    OraRoom rm = new OraRoom();
    OraIncludes_Meal mm = new OraIncludes_Meal();
    OraParking_Space pm = new OraParking_Space();


    private List<Integer> resNumList;

    public static void run(int id) {
        dialog = new ReservationHistory(id);
        dialog.pack();
        dialog.setVisible(true);

    }

    public ReservationHistory(int a) {
        guest = gm.getGuestById(a);
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        resNumList = resm.getAllReservationNumWithGuestID(a);
        ResList.setListData(resNumList.toArray());

        buttonOK.addActionListener(new ActionListener() {
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




        ResList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {

                Integer resNum = (int)ResList.getSelectedValue();
                RoomInfo room = rm.getRoomWithResrveNum(resNum);
                MakeReservationInfo reservation = resm.getReservationInfoWithReserveNum(resNum);
                roomNumberLabel.setText(room.getRoom_num()+"");
                checkInLabel.setText(reservation.getStart_date().toString());
                checkOutLabel.setText(reservation.getEnd_date().toString());
                guestNumberLabel.setText(reservation.getNumber_of_guest()+"");
                roomTypeLabel.setText(room.getType());
                discountLabel.setText("$"+reservation.getDiscount());

                if(mm.getMealWithReserveNum(resNum).isEmpty()){
                    mealLabel.setText("Meal Not Included");
                }else{
                    mealLabel.setText(mm.getMealWithReserveNum(resNum).toString());
                }

                if(pm.getParkingInfoWithReserveNum(resNum) == null){
                    parkingLabel.setText("Parking Not Included");
                }else{
                    parkingLabel.setText(pm.getParkingInfoWithReserveNum(resNum).getPlate_num()+" (Stall#"+pm.getParkingInfoWithReserveNum(resNum).getStall_num()+") ");
                }
            }
        });
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }
}
