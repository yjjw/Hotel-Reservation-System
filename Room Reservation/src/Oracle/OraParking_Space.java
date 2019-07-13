package Oracle;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import Object.Parking_SpraceInfo;
public class OraParking_Space {

    Random rand = new Random();
    Omanager manager;
    Connection con;

    public OraParking_Space(){
        manager = Omanager.getInstance();
        con = manager.getConnection();
    }

    public List<Parking_SpraceInfo> getParking() {
        List<Parking_SpraceInfo> parking = new ArrayList<>();

        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select * from Parking_Space");

            while(rs.next()) {
                int stall_num = rs.getInt("stall_num");
                String plate_num = rs.getString("plate_num");

                Parking_SpraceInfo pk = new Parking_SpraceInfo(plate_num, stall_num);
                parking.add(pk);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return parking;
    }

    public int InsertParking(String plate_num) {
        PreparedStatement ps;
        int stall_num = generateStall_num();
        try {
            ps = con.prepareStatement("insert into Parking_Space values (?,?)");
            ps.setString(1, plate_num);
            ps.setInt(2, stall_num);
            ps.executeUpdate();
            con.commit();
            ps.close();
            return stall_num;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }



    public int generateStall_num(){

        int stall_num = rand.nextInt(99); //randomly generate a number between 0 and 99
        if (isValidStall_num(stall_num)) {
            return generateStall_num();
        }
        return stall_num;
    }

    public boolean isValidStall_num(int stall_num) {
        try {
            Statement st = con.createStatement();
            String query = "select * from Parking_space where stall_num = " + stall_num;
            ResultSet rs = st.executeQuery(query);
            if (!rs.next()) return false;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }



    public Parking_SpraceInfo getParkingWithStallNum(int stall_num) {
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select * from Parking_Space where stall_num = " + stall_num);

            if(rs.next()) {
                String plate_num = rs.getString("plate_num");
                return new Parking_SpraceInfo(plate_num, stall_num);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }



    public Parking_SpraceInfo getParkingInfoWithReserveNum(int reserve_num){
        Parking_SpraceInfo ps = null;
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select s.stall_num, s.plate_num from Parking_Space s, Provides p where s.stall_num = p.stall_num and reserve_num = " + reserve_num);

            while(rs.next()) {
                int stall_num = rs.getInt("stall_num");
                String plate_num = rs.getString("plate_num");


                ps = new Parking_SpraceInfo(plate_num,stall_num);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ps;

    }



}
