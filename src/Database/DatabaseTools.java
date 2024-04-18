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

    public static void OpenConnection() {
        String dbURL = "jdbc:mysql://localhost:3306/" + schemaName;
        try {
            String username = "root";
            Scanner scan = new Scanner(System.in);
            System.out.println("Please enter database password:");
            String password = scan.nextLine();
            conn = DriverManager.getConnection(dbURL, username, password);
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
