package Controller;

import Database.DatabaseTools;
import Model.Customer;

import javax.swing.*;
import javax.xml.crypto.Data;
import java.sql.*;
import java.util.ArrayList;

public class CustomerDA {
    private ArrayList<Customer> customerList;

    public CustomerDA() {
        //populateCustomerList();
    }

    public ArrayList<Customer> getCustomerList() {
        populateCustomerList();
        return customerList;
    }

    private void populateCustomerList() {
        customerList = new ArrayList<Customer>();

        try {
            PreparedStatement ps = DatabaseTools.GetConnection().prepareStatement("SELECT * FROM customer WHERE isActive = TRUE");
            ResultSet rs = ps.executeQuery();

            Customer cust;
            while(rs.next()) {
                cust = new Customer();
                cust.setCustomerID(rs.getInt("Customer_ID"));
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

    public Customer getCustomer(int id) {
        Customer cust = new Customer();
        try{
            PreparedStatement ps = DatabaseTools.GetConnection().prepareStatement("SELECT * from customer where customer_id = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                cust.setCustomerID(rs.getInt("Customer_ID"));
                cust.setCustomerName_first(rs.getString("Customer_fName"));
                cust.setCustomerName_last(rs.getString("Customer_lName"));
                cust.setStreet_address(rs.getString("street_address"));
                cust.setCity(rs.getString("city"));
                cust.setState(rs.getString("state"));
                cust.setZip(rs.getString("zipcode"));
                cust.setEmail(rs.getString("email"));
                cust.setActive(rs.getBoolean("isActive"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cust;
    }

    public boolean addCustomer(Customer cust) {
        try{
            PreparedStatement ps = DatabaseTools.GetConnection().prepareStatement("Select (max(customer_id) + 1) as Customer_ID from customer");
            ResultSet rs = ps.executeQuery();
            int custId = 0;
            while(rs.next()) {
                custId = rs.getInt("Customer_ID");
            }
            ps = DatabaseTools.GetConnection().prepareStatement(
              "INSERT INTO customer(Customer_ID,Customer_fName,Customer_lName,email,street_address,city,state,zipCode)values(?,?,?,?,?,?,?,?) "
            );
            ps.setInt(1, custId);
            ps.setString(2, cust.getCustomerName_first());
            ps.setString(3, cust.getCustomerName_last());
            ps.setString(4, cust.getEmail());
            ps.setString(5, cust.getStreet_address());
            ps.setString(6, cust.getCity());
            ps.setString(7, cust.getState());
            ps.setString(8, cust.getZip());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean removeCustomer(int id) {
        try{
            int dialogResult = JOptionPane.showConfirmDialog(null, "Do you want to delete the customer", "Warning", JOptionPane.YES_NO_OPTION);
            if(dialogResult == JOptionPane.YES_OPTION) {
                PreparedStatement ps = DatabaseTools.GetConnection().prepareStatement("UPDATE customer set isActive = FALSE where customer_id = ?");
                ps.setInt(1, id);
                ps.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean updateCustomer(Customer cust) {
        try{
            PreparedStatement ps = DatabaseTools.GetConnection().prepareStatement(
                    "UPDATE customer set Customer_fName=?, Customer_lName=?,email=?,street_address=?,city=?,state=?,zipCode=? where Customer_ID = ?"
            );
            ps.setInt(8, cust.getCustomerID());
            ps.setString(1, cust.getCustomerName_first());
            ps.setString(2, cust.getCustomerName_last());
            ps.setString(3, cust.getEmail());
            ps.setString(4, cust.getStreet_address());
            ps.setString(5, cust.getCity());
            ps.setString(6, cust.getState());
            ps.setString(7, cust.getZip());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
