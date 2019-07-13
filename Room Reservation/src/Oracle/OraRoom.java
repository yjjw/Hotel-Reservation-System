package Oracle;

import Object.RoomInfo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class OraRoom {
    Omanager manager;
    Connection con;

    public OraRoom(){
        manager = Omanager.getInstance();
        con = manager.getConnection();
    }

    public List<RoomInfo> getRooms() {
        List<RoomInfo> rooms = new ArrayList<>();

        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select * from Room");

            while(rs.next()) {
                int room_num = rs.getInt("room_num");
                String type = rs.getString("type");
                double price = rs.getDouble("price");

                RoomInfo r = new RoomInfo(room_num,type,price);
                rooms.add(r);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rooms;
    }

    public double getPrice(int room_num) {
        try {
            Statement st = con.createStatement();
            String query = "select price from Room where room_num = " + room_num ;
            ResultSet rs = st.executeQuery(query);
            if (rs.next()) {
                return rs.getDouble("price");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    public List<Integer> getNumBySelectType(String type) {
        List<Integer> num = new ArrayList<>();
        try {
            Statement st = con.createStatement();
            String query = "select room_num from Room where type = '" + type +"'";
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                num.add(rs.getInt("room_num"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return num;
    }

    public RoomInfo getRoomByRoomNum(int room_num) {
        RoomInfo room;
        try {
            Statement st = con.createStatement();
            String query = "select * from Room where room_num = " + room_num;
            ResultSet rs = st.executeQuery(query);
            if (rs.next()) {
                String type = rs.getString("type");
                double price = rs.getDouble("price");
                room = new RoomInfo(room_num, type, price);
                return room;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<RoomInfo> getAllRoomWithLowerPrice(double p) {
        List<RoomInfo> rooms = new ArrayList<>();
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select * from Room where price < " + p);

            while(rs.next()) {
                int room_num = rs.getInt("room_num");
                String type = rs.getString("type");
                double price = rs.getDouble("price");
                RoomInfo room = new RoomInfo(room_num, type, price);
                rooms.add(room);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return rooms;
    }
    //select room number from Room where price < p, and return the corresponding room information,overload
    //reflects select and projection query
    public List<Integer> getRoomNumWithLowerPrice(double p) {
        List<Integer> roomsNum = new ArrayList<>();
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select DISTINCT room_num from Room where price < " + p);

            while(rs.next()) {
                    int room_num = rs.getInt("room_num");
                    roomsNum.add(room_num);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return roomsNum;
    }

    public List<String> getRoomTypeWithLowerPrice(double p) {

        List<String> roomType = new ArrayList<>();
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select DISTINCT type from Room where price < " + p);

            while(rs.next()) {
                    String type = rs.getString("type");
                    roomType.add(type);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return roomType;
    }

    // get all room with the given type and the price
    public List<Integer> getRoomWithTypeAndLowerPrice(String type, double p) {

        List<Integer> roomNum = new ArrayList<>();
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select room_num from Room where type = '" + type + "' and price < " + p);

            while(rs.next()) {
                int room_num = rs.getInt("room_num");
                roomNum.add(room_num);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return roomNum;
    }

    //select room_num, type, max(price) from Room
    //reflect aggregation
    public List<Integer> getRoomWithMaxPrice(){
        List<Integer> rooms = new ArrayList<>();
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select room_num from ROOM r where (price) IN (select max(price) from Room)");

            while(rs.next()) {
                int room_num = rs.getInt("room_num");

                rooms.add(room_num);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rooms;
    }

    //select room_num, type, min(price) from Room
    //reflect aggregation
    public List<Integer> getRoomWithMinPrice(){
        List<Integer> rooms = new ArrayList<>();
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select room_num from ROOM r where (price) IN (select min(price) from Room)");

            while(rs.next()) {
                int room_num = rs.getInt("room_num");

                rooms.add(room_num);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rooms;
    }

    public RoomInfo getRoomWithResrveNum(int reserve_num){
        RoomInfo ri = null;
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select r.room_num, r.type, r.price from Room r, Booked_At b where r.room_num = b.room_num and b.reserve_num = " + reserve_num);

            while(rs.next()) {
                int room_num = rs.getInt("room_num");
                String type = rs.getString("type");
                double price = rs.getDouble("price");

                ri = new RoomInfo(room_num,type,price);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ri;
    }


}
