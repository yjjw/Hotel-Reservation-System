package Oracle;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import Object.Includes_MealInfo;
import Object.MakeReservationInfo;

public class OraIncludes_Meal {
    Omanager manager;
    Connection con;

    public OraIncludes_Meal(){
        manager = Omanager.getInstance();
        con = manager.getConnection();
    }

    public List<Includes_MealInfo> getMeal() {
        List<Includes_MealInfo> meal = new ArrayList<>();
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select * from Includes_Meal");

            while (rs.next()) {
                int reserve_num = rs.getInt("reserve_num");
                String name = rs.getString("mname");

                Includes_MealInfo imi = new Includes_MealInfo(reserve_num,name);
                meal.add(imi);
            }

            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return meal;
    }

    public void InsertMeal(int reserve_num, List<String> name) {

        int i = 0;
        try {
            while (i<name.size()) {
                PreparedStatement ps;
                ps = con.prepareStatement("insert into Includes_Meal values (?,?)");
                ps.setInt(1, reserve_num);
                ps.setString(2, name.get(i));
                ps.executeUpdate();
                con.commit();
                ps.close();
                i++;
            }
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

    public List<String> getPopularMeals(){

        List<String> meals = new ArrayList<>();

        try {

            Statement st = con.createStatement();
            String query = "select distinct i.mname from Includes_Meal i where NOT EXISTS " +
                           "(select m.reserve_num from Make_Reservation m minus select im.reserve_num from Includes_Meal im where im.mname = i.mname)";
            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                String meal = rs.getString("mname");
                meals.add(meal);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return meals;

    }


    public List<String> getMealWithReserveNum(int reserve_num){
        List<String> im = new ArrayList<>();
        try {

            Statement st = con.createStatement();
            String query = "select * from Includes_Meal where reserve_num = " + reserve_num;
            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {

                im.add(rs.getString("mname"));


            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return im;

    }

    public List<String> allMeal(){
     List<String> meal = new ArrayList<>();
        try {
            Statement st = con.createStatement();
            String query = "select mname from Includes_Meal";
            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {

                meal.add(rs.getString("mname"));


            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        return meal;
    }


    public void cancelMeal(int reserve_num) {

        try {
            PreparedStatement ps = con.prepareStatement("DELETE from Includes_Meal WHERE reserve_num = " + reserve_num);
            ps.executeUpdate();
            con.commit();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
