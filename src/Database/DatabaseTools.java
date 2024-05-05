package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class DatabaseTools {
    private static Connection conn =  null;
    private static final String schemaName = "Eclipse_Collectibles";
    public static void DatabaseTools() {
    }

    public static Connection GetConnection() {
        if(conn == null) {
            OpenConnection();
        }
        return conn;
    }

    public static void TowsonConnection() {
        String dbURL = "jdbc:mysql://triton.towson.edu:3360/cbowen3db";
        try {
            String username = "cbowen3";
            String password = "";
            conn = DriverManager.getConnection(dbURL, username, password);
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void OpenConnection() {
        //TowsonConnection();
        if (conn == null) {
            System.out.println("COULD NOT CONNECT TO TOWSON, using LocalDB");
            String dbURL = "jdbc:mysql://localhost:3306/" + schemaName;
            try {
                Scanner scan = new Scanner(System.in);
                System.out.println("Please enter your username:");
                String username = "root";
                username = scan.nextLine();
                System.out.println("Please enter your password:");
                String password = scan.nextLine();
                //String username = "root";
                //String password = "";
                conn = DriverManager.getConnection(dbURL, username, password);
            }catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
