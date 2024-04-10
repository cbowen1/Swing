package Controller;

import Model.Customer;

import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class CustomerDA {
    private ArrayList<Customer> customerList;
    private int recordCursor;
    Connection conn;


    public CustomerDA() {
        setRecordCursorAtStart();
        createConnection("eclipse");
        populateCustomerList();

        for(Customer c : customerList) {
            System.out.println("WE CAN HAZ CUSTOMERS");
            System.out.println(c.getCustomerID());
            System.out.println(c.getCustomerName_first());
        }
    }

    private void populateCustomerList() {
        customerList = new ArrayList<Customer>();

        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM customer");
            ResultSet rs = ps.executeQuery();

            Customer cust;
            while(rs.next()) {
                cust = new Customer(rs.getString("custId"),
                        rs.getString("fName"),
                        rs.getString("lName"));
                customerList.add(cust);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setRecordCursorAtStart() {
        recordCursor = 0;
    }

    public void createConnection(String databaseName) {
        String dbURL = "jdbc:mysql://localhost:3306/" + databaseName;
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
