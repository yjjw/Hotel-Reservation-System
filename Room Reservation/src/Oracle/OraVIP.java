package Oracle;

import Object.VIPInfo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class OraVIP {
    Omanager manager;
    Connection con;

    public OraVIP() {
        manager = Omanager.getInstance();
        con = manager.getConnection();
    }

    public List<VIPInfo> getVIP() {
        List<VIPInfo> vip = new ArrayList<>();

        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select * from VIP");

            while(rs.next()) {
                int id = rs.getInt("id");
              //  String gname = rs.getString("gname");
             //   Date birthday = rs.getDate("birthday");
               // int phone = rs.getInt("phone_num");
              //  int credit = rs.getInt("credit_card_num");
                double points = rs.getDouble("points");

                VIPInfo vipInfo = new VIPInfo(id,points);
                vip.add(vipInfo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return vip;
    }

    public VIPInfo getVipWithID(int id) {
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select * from VIP where id = " + id);

            if(rs.next()) {
                double points = rs.getDouble("points");

                return new VIPInfo(id, points);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    //register a guest to be a VIP and initialize the points with 0
    public void BeAVip(int id) {

        double points = 0;
        PreparedStatement ps;
        try {
            ps = con.prepareStatement("INSERT INTO VIP VALUES (?,?)");
            ps.setInt(1, id);
            ps.setDouble(2, points);

            ps.executeUpdate();
            con.commit();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    public double getVipPoints(int id) {
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select points from VIP where id = " + id);

            if(rs.next()) {

                double points = rs.getDouble("points");

                return points;

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }


    public void updatePoints(int id, double consumePoints){
       updateVIP(id,getVipPoints(id) - consumePoints*10);
    }

    public void updateVIP( int id , double points) {
        try {
            PreparedStatement ps = con.prepareStatement("UPDATE VIP SET points = ? WHERE ID = ?");
            ps.setDouble(1, points);
            ps.setInt(2, id);

            ps.executeUpdate();
            con.commit();
            ps.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}