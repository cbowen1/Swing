package Database;

import javax.swing.*;
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
                var username = javax.swing.JOptionPane.showInputDialog("Please enter your username:");
                //root

                JPasswordField pf = new JPasswordField();
                int okCxl = JOptionPane.showConfirmDialog(null, pf, "Enter Password", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                String password = null;
                if (okCxl == JOptionPane.OK_OPTION) {
                    password = new String(pf.getPassword());
                }
                conn = DriverManager.getConnection(dbURL, username, password);
            }catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
