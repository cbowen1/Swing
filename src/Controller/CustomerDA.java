package Controller;

import Database.DatabaseTools;
import Model.Customer;

import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class CustomerDA {
    private ArrayList<Customer> customerList;
    private int recordCursor;

    public CustomerDA() {
        setRecordCursorAtStart();
        populateCustomerList();
    }

    public ArrayList<Customer> getCustomerList() {
        return customerList;
    }

    private void populateCustomerList() {
        customerList = new ArrayList<Customer>();

        try {
            PreparedStatement ps = DatabaseTools.GetConnection().prepareStatement("SELECT * FROM customer");
            ResultSet rs = ps.executeQuery();

            Customer cust;
            while(rs.next()) {
                cust = new Customer();
                cust.setCustomerID(rs.getString("Customer_ID"));
                cust.setCustomerName_first(rs.getString("Customer_fName"));
                cust.setCustomerName_last(rs.getString("Customer_lName"));
                cust.setEmail(rs.getString("email"));
                cust.setStreet_address(rs.getString("street_address"));
                cust.setCity(rs.getString("city"));
                cust.setState(rs.getString("state"));
                cust.setZip(rs.getString("zipCode"));
                customerList.add(cust);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setRecordCursorAtStart() {
        recordCursor = 0;
    }

    public void setRecordCursorAtEnd() {
        recordCursor = customerList.size()-1;
    }

    public Customer getCurrentCustomer() {
        return customerList.get(recordCursor);
    }

    public Customer getNextCustomer() {
        recordCursor = recordCursor >= customerList.size() -1 ? customerList.size()-1:recordCursor++;
        return customerList.get(recordCursor);
    }
}
