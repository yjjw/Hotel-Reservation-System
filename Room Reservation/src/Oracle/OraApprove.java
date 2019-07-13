package Oracle;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import Object.ApproveInfo;

public class OraApprove {
    Omanager manager;
    Connection con;

    public OraApprove() {
        manager = Omanager.getInstance();
        con = manager.getConnection();
    }

    //get information about approval
    public List<ApproveInfo> getApprove() {
        List<ApproveInfo> approvelist = new ArrayList<>();
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select * from Approve");

            while (rs.next()) {
                int reserve_num = rs.getInt("reserve_num");
                int employee_ID = rs.getInt("employee_ID");

                ApproveInfo ai = new ApproveInfo(reserve_num,employee_ID);
                approvelist.add(ai);
            }

            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return approvelist;
    }

    //insert the approve information with reservation number and corresponding employee ID who is in charge of
    //this reservation
    public void InsertApprove(int reserve_num,int employee_ID) {
        PreparedStatement ps;

        try {
            ps = con.prepareStatement("insert into Approve values (?,?)");
            ps.setInt(1, reserve_num);
            ps.setInt(2, employee_ID);

            ps.executeUpdate();
            con.commit();
            ps.close();
        }
        catch (SQLException ex1)
        {
            System.out.println("Message: " + ex1.getMessage());
            try
            {
                // undo the insert
                con.rollback();
            }
            catch (SQLException ex2)
            {
                System.out.println("Message: " + ex2.getMessage());
                System.exit(-1);
            }
        }

    }


    // return true if a reservation is approved
    public boolean approveOrNot(int reserve_num){

        try {
            Statement st = con.createStatement();
            String query = "select * from Approve where reserve_num = " + reserve_num;
            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {

                return true;
            }

            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }



    public void addPoints(int reserve_num){
        try{
            OraVIP VIP = new OraVIP();
            Statement st = con.createStatement();
            String query = "SELECT VIP.points AS point,Room.price AS prices, VIP.ID AS id FROM VIP JOIN Make_Reservation ON VIP.ID = Make_Reservation.ID JOIN Booked_At ON Make_Reservation.reserve_num = Booked_At.reserve_num JOIN Room ON Booked_At.room_num = Room.room_num WHERE Make_Reservation.reserve_num = " + reserve_num;
            ResultSet rs = st.executeQuery(query);
            if (rs.next()) {
                double points = rs.getDouble("point");
                double price = rs.getDouble("prices");
                int id = rs.getInt("id");
                System.out.println(points);
                System.out.println(price);
                System.out.println(id);
                points = points + price * 0.1;
                VIP.updateVIP(id, points);
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }


    public List<Integer> getApproveReserveNum(){
        List<Integer> unApprove = new ArrayList<>();
        try {
            Statement st = con.createStatement();
            String query = "select reserve_num from Approve";
            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {

                int ai = rs.getInt("reserve_num");;
                unApprove.add(ai);
            }

            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return unApprove;

    }


    public List<Integer> getUnApproveReserveNUm(){
        List<Integer> unApprove = new ArrayList<>();
        try {
            Statement st = con.createStatement();
            String query = "select m.reserve_num from Make_Reservation m minus select a.reserve_num from Approve a";
            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {

                int ai = rs.getInt("reserve_num");;
                unApprove.add(ai);
            }

            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return unApprove;

    }
}