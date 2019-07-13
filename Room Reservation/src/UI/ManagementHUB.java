package UI;

import Oracle.*;
import Object.*;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;


public class ManagementHUB {
    OraGuest gm = new OraGuest();;
    OraEmployee em = new OraEmployee();
    OraMakeReservation resm = new OraMakeReservation();
    OraRoom rm = new OraRoom();;
    OraIncludes_Meal mm = new OraIncludes_Meal() ;
    OraParking_Space pm= new OraParking_Space();
    OraVIP vm = new OraVIP();
    OraApprove am = new OraApprove();
    private List<Integer> gList= gm.getAllGuestID();
    private List<Integer> rList= am.getUnApproveReserveNUm();
    private List<Integer> pList= new ArrayList<Integer>();
    private JList guestList;
    private JList ResList;
    private JPanel tab1;
    private JPanel tab2;
    private JPanel resListPanel;
    private JPanel ReslistPanelInner;
    private JPanel resSearchPanel;
    private EmployeeInfo employee;
    private JButton guestSearchButton;
    private JPanel mainPanel;
    private JPanel topPanel;
    private JLabel nameTxt;
    private JPanel infoButtonPanel;
    private JButton InfoButton;
    private JPanel viewPanel;
    private JTabbedPane tabbedPane1;
    private JLabel rlTitle;
    private JRadioButton reservationNumberRadioButton;
    private JRadioButton guestPhoneNumberRadioButton;
    private JTextField guestSearchBar;
    private JPanel filterPanel;
    private JRadioButton reservationRadioButton;
    private JRadioButton guestPhoneRadioButton;
    private JRadioButton guestIDRadioButton;
    private JPanel infoPanel;
    private JLabel checkInLabel;
    private JLabel checkOutLabel;
    private JLabel guestNumberLabel;
    private JLabel roomTypeLabel;
    private JLabel discountLabel;
    private JLabel mealLabel;
    private JLabel roomNumberLabel;
    private JLabel parkingLabel;
    private JLabel nameLabel;
    private JLabel bdayLabel;
    private JLabel phoneLabel;
    private JLabel idLabel;
    private JLabel pointLabel;
    private JLabel cardLabel;
    private JRadioButton approvedRadioButton;
    private JButton resSearchButton;
    private JButton editButton;
    private JButton approveButton;
    private JButton cancelButton;
    private JButton deleteButton;
    private JRadioButton allUnapprovedRadioButton;
    private JTextField resSearchBar;
    private JRadioButton allGuestRadioButton;
    private JRadioButton gGuestIDRadioButton;
    private JRadioButton gGuestPhoneRadioButton;
    private JButton cancelMealButton;
    private JButton showAllGuestButton;
    private JButton showMostDiscountUserButton;
    private JButton showLeastDiscountUserButton;
    private JList pointList;
    private JLabel ADULabel;
    private static JFrame frame = new JFrame("ManagementHUB");
    private List<MakeReservationInfo> ana = new ArrayList<MakeReservationInfo>();
    public static void run(Integer id) {

        frame.setContentPane(new ManagementHUB(id).mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
    public void refreshResList(){
        rList.clear();
        if(allUnapprovedRadioButton.isSelected()){
            rList.addAll(am.getUnApproveReserveNUm());
            resSearchBar.setEnabled(false);
        }else if(approvedRadioButton.isSelected()){
            rList.addAll(am.getApproveReserveNum());
            resSearchBar.setEnabled(false);
        }else if(reservationNumberRadioButton.isSelected()){
            String s = resSearchBar.getText();
            resSearchBar.setEnabled(true);
            try{
                if(s.isEmpty()){
                    rList.addAll(resm.getAllReservationNum());
                }else{
                    rList.add(resm.getReservationInfoWithReserveNum(Integer.parseInt(s)).getReserve_num());
                }

            }catch(NumberFormatException exp){
                JOptionPane.showMessageDialog(frame, "ERROR");
            }
        }else if(guestIDRadioButton.isSelected()){
            String s = resSearchBar.getText();
            resSearchBar.setEnabled(true);
            try{
                if(s.isEmpty()){
                    rList.addAll(resm.getAllReservationNum());
                }else{
                    rList.addAll(resm.getAllReservationNumWithGuestID(Integer.parseInt(s)));
                }

            }catch(NumberFormatException exp){
                JOptionPane.showMessageDialog(frame, "ERROR");
            }
        }else if(guestPhoneNumberRadioButton.isSelected()){
            String s = resSearchBar.getText();
            resSearchBar.setEnabled(true);
            try{
                if(s.isEmpty()){
                    rList.addAll(resm.getAllReservationNum());
                }else {
                    rList.addAll(resm.getAllReservationNumWithGuestPhoneNum(Long.parseLong(s)));
                }

            }catch(NumberFormatException exp){
                JOptionPane.showMessageDialog(frame, "ERROR");
            }
        }else{
            resSearchBar.setEnabled(false);

        }

        ResList.setListData(rList.toArray());
    }
    public void refreshGuestList(){
        gList.clear();
        if(allGuestRadioButton.isSelected()){
            gList.addAll(gm.getAllGuestID());
        }else if(gGuestIDRadioButton.isSelected()){
            String s = guestSearchBar.getText();
            try{
                if(s.isEmpty()){
                    gList.addAll(gm.getAllGuestID());
                }else{
                    if(gm.getGuestById(Integer.parseInt(s)) != null)
                        gList.add(gm.getGuestById(Integer.parseInt(s)).getID());
                }

            }catch(NumberFormatException exp){
                JOptionPane.showMessageDialog(frame, "ERROR");
            }
        }else if(gGuestPhoneRadioButton.isSelected()){
            String s = guestSearchBar.getText();
            guestSearchBar.setEnabled(true);
            try{
                if(s.isEmpty()){
                    gList.addAll(gm.getAllGuestID());
                }else {
                    if(gm.getGuestByPhoneNumber(Long.parseLong(s)) != null)
                        gList.add(gm.getGuestByPhoneNumber(Long.parseLong(s)).getID());
                }

            }catch(NumberFormatException exp){
                JOptionPane.showMessageDialog(frame, "ERROR");
            }
        }else{
            gList.addAll(gm.getAllGuestID());
            guestSearchBar.setEnabled(false);
        }

        guestList.setListData(gList.toArray());
    }
    public ManagementHUB(int a) {

        employee = em.getEmployeeById(a);
        nameTxt.setText("Welcom! "+employee.getName());
        guestList.setListData(gList.toArray());
        ResList.setListData(rList.toArray());
        allUnapprovedRadioButton.setSelected(true);
        allGuestRadioButton.setSelected(true);
        ana = resm.AverageDiscountForEachGuest();

        ana = resm.AverageDiscountForEachGuest();
        pList.clear();
        for(int i = 0;i<ana.size();i++){
            pList.add(ana.get(i).getID());
        }
        pointList.setListData(pList.toArray());
        guestSearchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refreshGuestList();
            }
        });
        InfoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                InfoEmployee.run(employee.getID());
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
                if(am.approveOrNot(resNum)){
                    approveButton.setEnabled(false);
                }else{
                    approveButton.setEnabled(true);
                }
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
        guestList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                GuestInfo guest = gm.getGuestById((Integer)guestList.getSelectedValue());
                nameLabel.setText(guest.getName());
                bdayLabel.setText(guest.getBirthday().toString());
                phoneLabel.setText(guest.getPhoneNum()+"");
                cardLabel.setText(guest.getCredit_card_num()+"");
                idLabel.setText(guest.getID()+"");
                if(vm.getVipWithID(guest.getID()) == null){
                    pointLabel.setText("You are not a Member!");
                }else{
                    pointLabel.setText(""+vm.getVipWithID(guest.getID()).getPoints());
                }
            }
        });
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                InfoEditGuest.run((Integer)guestList.getSelectedValue());
                GuestInfo guest = gm.getGuestById((Integer)guestList.getSelectedValue());
                nameLabel.setText(guest.getName());
                bdayLabel.setText(guest.getBirthday().toString());
                phoneLabel.setText(guest.getPhoneNum()+"");
                cardLabel.setText(guest.getCredit_card_num()+"");
                idLabel.setText(guest.getID()+"");
                if(vm.getVipWithID(guest.getID()) == null){
                    pointLabel.setText("You are not a Member!");
                }else{
                    pointLabel.setText(""+vm.getVipWithID(guest.getID()).getPoints());
                }
            }
        });
        approveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRes = (Integer)ResList.getSelectedValue();
                am.InsertApprove(selectedRes,employee.getID());
                JOptionPane.showMessageDialog(frame, "Employee"+employee.getName()+"("+employee.getID()+") Approved Reservation "+ selectedRes);
                am.addPoints(selectedRes);
                refreshResList();

            }
        });
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRes = (Integer)ResList.getSelectedValue();
                resm.deleteReservation(selectedRes);
                refreshResList();
            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectGuest = (Integer)guestList.getSelectedValue();
                gm.deleteGuest(selectGuest);
                refreshGuestList();

            }
        });
        reservationNumberRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resSearchBar.setText("");
                refreshResList();
            }
        });
        guestIDRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resSearchBar.setText("");
                refreshResList();
            }
        });
        guestPhoneNumberRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resSearchBar.setText("");
                refreshResList();
            }
        });
        allUnapprovedRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resSearchBar.setText("");
                refreshResList();
            }
        });
        approvedRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resSearchBar.setText("");
                refreshResList();
            }
        });
        resSearchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refreshResList();
            }
        });
        cancelMealButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Integer resNum = (int)ResList.getSelectedValue();

                mm.cancelMeal(resNum);

                RoomInfo room = rm.getRoomWithResrveNum(resNum);
                MakeReservationInfo reservation = resm.getReservationInfoWithReserveNum(resNum);
                roomNumberLabel.setText(room.getRoom_num()+"");
                checkInLabel.setText(reservation.getStart_date().toString());
                checkOutLabel.setText(reservation.getEnd_date().toString());
                guestNumberLabel.setText(reservation.getNumber_of_guest()+"");
                roomTypeLabel.setText(room.getType());
                discountLabel.setText("$"+reservation.getDiscount());
                if(am.approveOrNot(resNum)){
                    approveButton.setEnabled(false);
                }else{
                    approveButton.setEnabled(true);
                }
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
        allGuestRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guestSearchBar.setText("");
                guestSearchBar.setEnabled(false);
                refreshGuestList();

            }
        });
        gGuestIDRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guestSearchBar.setText("");
                guestSearchBar.setEnabled(true);
                refreshGuestList();

            }
        });
        gGuestPhoneRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guestSearchBar.setText("");
                guestSearchBar.setEnabled(true);
                refreshGuestList();
            }
        });


        showAllGuestButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ana = resm.AverageDiscountForEachGuest();
                pList.clear();
                for(int i = 0;i<ana.size();i++){
                    pList.add(ana.get(i).getID());
                }
                pointList.setListData(pList.toArray());
            }
        });
        showMostDiscountUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ana = resm.getIDOfMaxOfAverageDiscount();
                pList.clear();
                for(int i = 0;i<ana.size();i++){
                    pList.add(ana.get(i).getID());
                }
                pointList.setListData(pList.toArray());
            }
        });
        showLeastDiscountUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ana = resm.getIDOfMinOfAverageDiscount();
                pList.clear();
                for(int i = 0;i<ana.size();i++){
                    pList.add(ana.get(i).getID());
                }
                pointList.setListData(pList.toArray());

            }
        });

        pointList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int pointID = (Integer) pointList.getSelectedValue();
                for(int i = 0;i<ana.size();i++){
                    if(ana.get(i).getID() == pointID){
                        ADULabel.setText(""+ana.get(i).getDiscount());
                    }
                }
            }
        });
    }


}
