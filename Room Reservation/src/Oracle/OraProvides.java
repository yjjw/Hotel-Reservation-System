package Oracle;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import Object.ProvidesInfo;

public class OraProvides {

    Omanager manager;
    Connection con;

    public OraProvides(){
        manager = Omanager.getInstance();
        con = manager.getConnection();
    }

    public List<ProvidesInfo> getProvides() {
        List<ProvidesInfo> provides = new ArrayList<>();

        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select * from Provides");

            while(rs.next()) {
                int reserve_num = rs.getInt("reserve_num");
                int stall_num = rs.getInt("stall_num");

                ProvidesInfo p = new ProvidesInfo(reserve_num, stall_num);
                provides.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return provides;
    }

    public void addProvidesInfo(int reserve_num, int stall_num) {
        try {
            PreparedStatement ps = con.prepareStatement("insert into Provides values (?,?)");
            ps.setInt(1,reserve_num);
            ps.setInt(2,stall_num);
            ps.executeUpdate();
            con.commit();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Integer> getStall_num(int reserve_num) {
        List<Integer> num = new ArrayList<>();
        try {
            Statement st = con.createStatement();
            String query = "select stall_num from Provides where reserve_num = " +  reserve_num;
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                num.add(rs.getInt("stall_num"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return num;
    }



}
