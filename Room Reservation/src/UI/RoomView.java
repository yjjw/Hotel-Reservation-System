package UI;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import Oracle.OraGuest;
import Object.GuestInfo;
import Oracle.OraRoom;


public class RoomView {

    private JPanel mainPanel;
    private JButton ResButton;
    private JButton InfoButton;
    private JPanel topPanel;
    private JLabel nameTxt;
    private JPanel infoButtonPanel;
    private JPanel viewPanel;
    private JPanel filterPanel;
    private JList roomList;
    private JButton reserveButton;
    private JLabel typeTxt;
    private JLabel priceTxt;
    private JLabel priceField;
    private JLabel typeField;
    private JButton searchButton;
    private JPanel infoPanel;
    private JLabel rlTitle;
    private JComboBox typeComboBox;
    private JCheckBox searchRoomWithPriceCheckBox;
    private JCheckBox searchRoomByTypeCheckBox;
    private JRadioButton searchByUsingFilterRadioButton;
    private JRadioButton directSearchByRoomRadioButton;
    private JTextField rmNumField;
    private JButton resetButton;
    private JTextField priceTxtField;
    private JRadioButton maximumPriceRadioButton;
    private JRadioButton minimumPriceRadioButton;
    private OraGuest gm = new OraGuest();
    private GuestInfo guest;
    private static JFrame frame = new JFrame("RoomView");
    private OraRoom rm = new OraRoom();
    private List<Integer> roomNumList = rm.getRoomNumWithLowerPrice(100000000);

    private RoomView(long id, int type) {
        roomList.setListData(roomNumList.toArray());
        searchRoomByTypeCheckBox.setEnabled(true);
        searchRoomWithPriceCheckBox.setEnabled(true);
        priceTxtField.setEnabled(false);
        typeComboBox.setEnabled(false);
        rmNumField.setEnabled(false);

        if(type == 0){
            guest = gm.getGuestById((int)id);
        }
        else{
            guest = gm.getGuestByPhoneNumber(id);
        }
        nameTxt.setText("Welcome, "+guest.getName()+ " !");
        reserveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(roomList.getSelectedValue() != null){
                    Integer tempRm = (int)roomList.getSelectedValue();
                    ReservationForm.run(tempRm,guest.getID());
                }else{
                    JOptionPane.showMessageDialog(frame, "Select a room from room list!");
                }
            }
        });
        ResButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ReservationHistory.run(guest.getID());
            }
        });
        InfoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                InfoGuest.run(guest.getID());
            }
        });
        searchByUsingFilterRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchRoomByTypeCheckBox.setEnabled(true);
                searchRoomByTypeCheckBox.setSelected(false);
                searchRoomWithPriceCheckBox.setEnabled(true);
                searchRoomWithPriceCheckBox.setSelected(false);
                priceTxtField.setEnabled(false);
                typeComboBox.setEnabled(false);
                rmNumField.setEnabled(false);

            }
        });
        directSearchByRoomRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchRoomByTypeCheckBox.setEnabled(false);
                searchRoomWithPriceCheckBox.setEnabled(false);
                priceTxtField.setEnabled(false);
                typeComboBox.setEnabled(false);
                rmNumField.setEnabled(true);
            }
        });
        searchRoomWithPriceCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(searchRoomWithPriceCheckBox.isSelected()){
                    priceTxtField.setEnabled(true);
                }else{
                    priceTxtField.setEnabled(false);
                }
            }
        });
        searchRoomByTypeCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(searchRoomByTypeCheckBox.isSelected()){
                    typeComboBox.setEnabled(true);
                }else{
                    typeComboBox.setEnabled(false);
                }
            }
        });
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchByUsingFilterRadioButton.setSelected(true);
                
                searchRoomByTypeCheckBox.setEnabled(true);
                searchRoomWithPriceCheckBox.setEnabled(true);
                priceTxtField.setEnabled(false);
                typeComboBox.setEnabled(false);
                rmNumField.setEnabled(false);
                roomList.setListData(roomNumList.toArray());
                roomNumList.clear();
                roomNumList.addAll(rm.getRoomNumWithLowerPrice(100000000));
            }
        });
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(searchByUsingFilterRadioButton.isSelected()){
                    if(searchRoomByTypeCheckBox.isSelected()&&searchRoomWithPriceCheckBox.isSelected()){
                        try{
                            roomNumList.clear();
                            if (rm.getRoomWithTypeAndLowerPrice(typeComboBox.getSelectedItem().toString(),Double.parseDouble(priceTxtField.getText())).size()==0) {
                                JOptionPane.showMessageDialog(frame, "Price too low!");
                                return;
                            }
                            else {
                                roomNumList.addAll(rm.getRoomWithTypeAndLowerPrice(typeComboBox.getSelectedItem().toString(),Double.parseDouble(priceTxtField.getText())));
                                roomList.setListData(roomNumList.toArray());
                            }

                        }catch(Exception exp){
                            JOptionPane.showMessageDialog(frame, "Invalid Price Number");
                            return;
                        }
                    }else if(searchRoomByTypeCheckBox.isSelected()){

                        roomNumList.clear();
                        roomNumList.addAll(rm.getNumBySelectType(typeComboBox.getSelectedItem().toString()));
                        roomList.setListData(roomNumList.toArray());

                    }else if(searchRoomWithPriceCheckBox.isSelected()){

                        try{
                            roomNumList.clear();
                            if(rm.getRoomNumWithLowerPrice(Double.parseDouble(priceTxtField.getText())) == null){
                                JOptionPane.showMessageDialog(frame, "Price too low!");
                                return;
                            }else {
                                roomNumList.addAll(rm.getRoomNumWithLowerPrice(Double.parseDouble(priceTxtField.getText())));
                                roomList.setListData(roomNumList.toArray());
                            }

                        }catch(Exception exp){
                            JOptionPane.showMessageDialog(frame, "Invalid Price Number");
                            return;
                        }

                    }else{
                        JOptionPane.showMessageDialog(frame, "At least use one of the filters if you chose to use filter :)");
                        return;
                    }
                }else if(directSearchByRoomRadioButton.isSelected()){
                    try{
                        roomNumList.clear();
                        if(rm.getRoomByRoomNum(Integer.parseInt(rmNumField.getText())) == null){
                            JOptionPane.showMessageDialog(frame, "Room does not exist!");
                            return;
                        }else {
                            roomNumList.add(rm.getRoomByRoomNum(Integer.parseInt(rmNumField.getText())).getRoom_num());
                            roomList.setListData(roomNumList.toArray());
                        }

                    }catch(Exception exp){
                        JOptionPane.showMessageDialog(frame, "Invalid Room Number");
                        return;
                    }

                }else if(maximumPriceRadioButton.isSelected()) {
                    roomNumList.clear();
                    roomNumList.addAll(rm.getRoomWithMaxPrice());
                    roomList.setListData(roomNumList.toArray());
                }else if(minimumPriceRadioButton.isSelected()) {
                    roomNumList.clear();
                    roomNumList.addAll(rm.getRoomWithMinPrice());
                    roomList.setListData(roomNumList.toArray());
                }else{
                    JOptionPane.showMessageDialog(frame, "Chose one of the search methods before pressing the button :)");
                }
            }
        });
        roomList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                Integer tempRm = (int)roomList.getSelectedValue();
                typeField.setText(rm.getRoomByRoomNum(tempRm).getType());
                priceField.setText(""+rm.getRoomByRoomNum(tempRm).getPrice());
            }
        });
        maximumPriceRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchRoomByTypeCheckBox.setEnabled(false);
                searchRoomWithPriceCheckBox.setEnabled(false);
                priceTxtField.setEnabled(false);
                typeComboBox.setEnabled(false);
                rmNumField.setEnabled(false);
            }
        });
        minimumPriceRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchRoomByTypeCheckBox.setEnabled(false);
                searchRoomWithPriceCheckBox.setEnabled(false);
                priceTxtField.setEnabled(false);
                typeComboBox.setEnabled(false);
                rmNumField.setEnabled(false);
            }
        });
    }

    public static void run(long id,int type) {
        frame.setContentPane(new RoomView(id,type).mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

}
