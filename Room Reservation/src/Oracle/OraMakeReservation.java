package Oracle;

import java.sql.*;
import java.sql.Date;
import java.util.*;

import Object.MakeReservationInfo;

public class OraMakeReservation {
    Random rand;
    Omanager manager;
    Connection con;

    public OraMakeReservation(){
        manager = Omanager.getInstance();
        con = manager.getConnection();
    }

    public List<MakeReservationInfo> getGuest() {
        List<MakeReservationInfo> reservation = new ArrayList<>();
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select * from Make_Reservation");

            while (rs.next()) {
                int reserve_num = rs.getInt("reserve_num");
                int number_of_guest = rs.getInt("number_of_guest");
                Date start_date = rs.getDate("start_date");
                Date end_date = rs.getDate("end_date");
                double discount = rs.getDouble("discount");
                int ID = rs.getInt("ID");

                MakeReservationInfo gi = new MakeReservationInfo(reserve_num, number_of_guest, start_date, end_date, discount, ID);
                reservation.add(gi);
            }

            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reservation;
    }

    public int InsertReservation(int number_of_guest, Date start_date, Date end_date, double discount, int ID) {
        PreparedStatement ps;
        int i = -1;
        try {
            int reserve_num = generateReserveNum();
            ps = con.prepareStatement("INSERT INTO Make_Reservation VALUES (?,?,?,?,?,?)");
            ps.setInt(1, reserve_num);

            ps.setInt(2, number_of_guest);
            ps.setDate(3, start_date);
            ps.setDate(4, end_date);
            ps.setDouble(5, discount);


            ps.setInt(6, ID);

            ps.executeUpdate();
            con.commit();
            ps.close();
            return reserve_num;
        }
        catch (SQLException ex)
        {
            System.out.println("Message: " + ex.getMessage());
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
        return i;
    }



    public void deleteReservation(int reserve_num) {
            try {
                PreparedStatement ps = con.prepareStatement("delete from Make_Reservation WHERE reserve_num = " + reserve_num);
                ps.executeUpdate();
                con.commit();
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
    }

    public int generateReserveNum() {
        rand = new Random();
        int tid = rand.nextInt(89999) + 10000;
        if(isInValidNum(tid))
            generateReserveNum();
        return tid;
    }

    public boolean isInValidNum(int num) {
        try {
            Statement st = con.createStatement();
            String query = "select * from Make_Reservation where reserve_num = " + num;
            ResultSet rs = st.executeQuery(query);
            if (!rs.next()) return false;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }


    public List<Integer> getAllReservationNumWithGuestID(int ID){
        List<Integer> ret = new ArrayList<>();
        try {

            Statement st = con.createStatement();
            String query = "select reserve_num from Make_Reservation where ID = " + ID;
            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                int reserve_num = rs.getInt("reserve_num");

                ret.add(reserve_num);
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ret;
    }

    // Join query: get all reservation num with guests' phone number
    public List<Integer> getAllReservationNumWithGuestPhoneNum(long phone_num){
        List<Integer> ret = new ArrayList<>();
        try {

            Statement st = con.createStatement();
            String query = "select reserve_num from Make_Reservation m, Guest g where m.ID = g.ID and phone_num = " + phone_num;
            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                int reserve_num = rs.getInt("reserve_num");

                ret.add(reserve_num);
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ret;
    }

    public List<Integer> getAllReservationNum(){
        List<Integer> ret = new ArrayList<>();
        try {

            Statement st = con.createStatement();
            String query = "select reserve_num from Make_Reservation";
            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                int reserve_num = rs.getInt("reserve_num");

                ret.add(reserve_num);
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ret;
    }

    public List<MakeReservationInfo> getReservationWithEmployee(int employee_id) {
        List<MakeReservationInfo> ret = new ArrayList<>();

        try {
            createReservationWithEmployee();
            Statement st = con.createStatement();
            String query = "select reserve_num, number_of_guest, start_date, end_date, discount, ID from reserve_with_employee where employee_ID = " + employee_id;
            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                int reserve_num = rs.getInt("reserve_num");
                int number_of_guest = rs.getInt("number_of_guest");
                Date start_date = rs.getDate("start_date");
                Date end_date = rs.getDate("end_date");
                double discount = rs.getDouble("discount");
                int id = rs.getInt("ID");

                MakeReservationInfo m = new MakeReservationInfo(reserve_num, number_of_guest, start_date, end_date, discount, id);
                ret.add(m);
            }
            dropReservationWithEmployee();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ret;
    }



    private void createReservationWithEmployee() {
        try {
            Statement st = con.createStatement();
            String query = "create view reserve_with_employee as "
                    + "select Employee.ename, Employee.employee_ID, Employee.phone_num, "
                    + "Make_Reservation.reserve_num, Make_Reservation.number_of_guest, Make_Reservation.start_date,Make_Reservation.end_date, Make_Reservation.discount, Make_Reservation.ID "
                    + "from Employee join Approve on Employee.employee_ID = Approve.employee_ID"
                    + " join Make_Reservation on Make_Reservation.reserve_num = Approve.reserve_num";
            st.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void dropReservationWithEmployee() {
        try {
            Statement st = con.createStatement();
            st.executeQuery("drop view reserve_with_employee");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public MakeReservationInfo getReservationInfoWithReserveNum(int reserve_num){
        MakeReservationInfo mr = null;
        try {

            Statement st = con.createStatement();
            String query = "select * from Make_Reservation where reserve_num = " + reserve_num;
            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {

                int number_of_guest = rs.getInt("number_of_guest");
                Date start_date = rs.getDate("start_date");
                Date end_date = rs.getDate("end_date");
                double discount = rs.getDouble("discount");
                int ID = rs.getInt("ID");

                mr = new MakeReservationInfo(reserve_num, number_of_guest,start_date,end_date,discount,ID);
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return mr;
    }

    public List<MakeReservationInfo> getIDOfMaxOfAverageDiscount(){
        List<MakeReservationInfo> mr = new ArrayList<>();
        try {
            getViewAverageDiscountForEachGuest();
            Statement st = con.createStatement();
            String query = "SELECT id, avg_dis FROM avg_discount WHERE (avg_dis) IN (SELECT max(avg_dis) FROM avg_discount)";
            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                int id = rs.getInt("id");
                double discount = rs.getDouble("avg_dis");
                mr.add(new MakeReservationInfo(0,0,null,null,discount,id));
            }
            dropAverageDiscountForEachGuest();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return mr;

    }

    public List<MakeReservationInfo> getIDOfMinOfAverageDiscount(){

        List<MakeReservationInfo> mr = new ArrayList<>();
        try {
            getViewAverageDiscountForEachGuest();
            Statement st = con.createStatement();
            String query = "SELECT id, avg_dis FROM avg_discount WHERE (avg_dis) IN (SELECT min(avg_dis) FROM avg_discount)";
            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                int id = rs.getInt("id");
                double discount = rs.getDouble("avg_dis");
                mr.add(new MakeReservationInfo(0,0,null,null,discount,id));
            }
            dropAverageDiscountForEachGuest();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return mr;

    }

    public List<MakeReservationInfo> AverageDiscountForEachGuest(){

        List<MakeReservationInfo> mr = new ArrayList<>();
        try {
            getViewAverageDiscountForEachGuest();
            Statement st = con.createStatement();
            String query = "select * from avg_discount";
            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                int id = rs.getInt("id");
                double discount = rs.getDouble("avg_dis");
                mr.add(new MakeReservationInfo(0,0,null,null,discount,id));
            }
            dropAverageDiscountForEachGuest();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return mr;
    }



    public void getViewAverageDiscountForEachGuest(){

        try {
            Statement st = con.createStatement();
            String query = "create view avg_discount as select ID as id, avg(discount) as avg_dis from Make_Reservation GROUP BY ID";

            st.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    private void dropAverageDiscountForEachGuest() {
        try {
            Statement st = con.createStatement();
            st.executeQuery("drop view avg_discount");
            con.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
