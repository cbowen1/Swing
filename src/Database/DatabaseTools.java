package Database;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class DatabaseTools {
    private static Connection conn =  null;

    private static Component parent = null;
    private static final String schemaName = "Eclipse_Collectibles";
    public static void DatabaseTools() {
    }

    public static Connection GetConnection() {
        if(conn == null) {
            OpenConnection();
        }
        return conn;
    }

    public void setParent(Component parent){
        this.parent = parent;
    }

    public static void TowsonConnection() {
        String dbURL = "jdbc:mysql://triton.towson.edu:3360/";
        try {
            Scanner scan = new Scanner(System.in);
            JTextField tf = new JTextField();

            int okCxl = JOptionPane.showConfirmDialog(parent, tf, "Enter your Towson username:", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            String username;
            if (okCxl == JOptionPane.OK_OPTION) {
                username = new String(tf.getText());
            } else {
                return;
            }

            JPasswordField pf = new JPasswordField();
            okCxl = JOptionPane.showConfirmDialog(parent, pf, "Enter Towson Password", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            String password = null;
            if (okCxl == JOptionPane.OK_OPTION) {
                password = new String(pf.getPassword());
            } else {
                return;
            }
            tf.setText("");
            okCxl = JOptionPane.showConfirmDialog(parent, tf, "Enter your Towson database:", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            String database;
            if (okCxl == JOptionPane.OK_OPTION) {
                database = new String(tf.getText());
            } else {
                return;
            }
            dbURL = dbURL + database;
            conn = DriverManager.getConnection(dbURL, username, password);
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void OpenConnection() {
        TowsonConnection();
        if (conn == null) {
            System.out.println("COULD NOT CONNECT TO TOWSON database, using Local");
            String dbURL = "jdbc:mysql://localhost:3306/" + schemaName;
            try {
                Scanner scan = new Scanner(System.in);

                JTextField tf = new JTextField();
                tf.addComponentListener(new ComponentAdapter() {
                    public void componentShown(ComponentEvent ce) {

                        tf.requestFocus();
                    }
                });
                int okCxl = JOptionPane.showConfirmDialog(parent, tf, "Enter your mysql username:", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                String username;
                if (okCxl == JOptionPane.OK_OPTION) {
                    username = new String(tf.getText());
                } else {
                    return;
                }

                JPasswordField pf = new JPasswordField();
                okCxl = JOptionPane.showConfirmDialog(parent, pf, "Enter Password", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                String password = null;
                if (okCxl == JOptionPane.OK_OPTION) {
                    password = new String(pf.getPassword());
                } else {
                    return;
                }
                conn = DriverManager.getConnection(dbURL, username, password);
            }catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
