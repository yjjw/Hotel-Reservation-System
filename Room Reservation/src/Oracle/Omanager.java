package Oracle;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Omanager {


        private Connection c;
        private Statement stmt;
        private static Omanager instance = new Omanager();

        public Omanager() {
        }

        public static Omanager getInstance() {
            return instance;
        }

        public Connection getConnection() {
            if (c == null) {
                try {
                   DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
                    c = DriverManager.getConnection("jdbc:oracle:thin:@dbhost.ugrad.cs.ubc.ca:1522:ug", "ora_v4l0b", "a26821158");
                 //   c = DriverManager.getConnection("jdbc:oracle:thin:@dbhost.ugrad.cs.ubc.ca:1522:ug", "ora_x2x0b", "a13571154");

                    //  c = DriverManager.getConnection("jdbc:oracle:thin:@dbhost.ugrad.cs.ubc.ca:1522:ug", "ora_w4u0b", "a30425169");
                  //  c = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1522:ug", "ora_v4l0b", "a26821158");
                    System.out.println("Connection succeeded");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            return c;
        }

        public int execute(String executeString){
            int rowCount = -1;
            try {
                rowCount = stmt.executeUpdate(executeString);
                System.out.println("row " + rowCount + " updated");
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println(executeString + " : update fails");
            }
            return rowCount;
        }

        public void disconnect(){
            try {
                c.close();
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("disconnect fails");
            }
        }




}
