package Oracle;

import Object.EmployeeInfo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;



public class OraEmployee {

    Omanager manager;
    Connection con;
    Random rand;

    public OraEmployee() {
        manager = Omanager.getInstance();
        con = manager.getConnection();
    }

    public List<EmployeeInfo> getEmployees() {
        List<EmployeeInfo> employees = new ArrayList<>();

        try {
            Statement st = con.createStatement(); //A Statement is an interface that represents a SQL statement
            ResultSet rs = st.executeQuery("select * from Employee"); //rs is a table of data representing a database result set

            while(rs.next()) {
                String ename = rs.getString("ename");
                int employee_id = rs.getInt("employee_ID");
                long phone_num = rs.getLong("phone_num");

                EmployeeInfo employeeInfo = new EmployeeInfo(ename,employee_id,phone_num);
                employees.add(employeeInfo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return employees;
    }

    public EmployeeInfo getEmployeeById(int eid) {
        EmployeeInfo ei = null;
        try {
            Statement st = con.createStatement();
            String query = "select * from Employee where employee_ID = " + eid;
            ResultSet rs = st.executeQuery(query);
            while(rs.next()) {
                String ename = rs.getString("ename");
                int employee_ID = rs.getInt("employee_ID");
                long phone_num = rs.getLong("phone_num");
                ei = new EmployeeInfo(ename,employee_ID,phone_num);
            }
            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ei;
    }


    public boolean isValidEID(int employee_ID) {
        try {
            Statement st = con.createStatement();
            String query = "select * from Employee where employee_ID = " + employee_ID;
            ResultSet rs = st.executeQuery(query);
            if (!rs.next()) return false;

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("cool");
        }
        return true;
    }





}

