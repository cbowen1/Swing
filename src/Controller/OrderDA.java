package Controller;

import Database.DatabaseTools;
import Model.Order;

import javax.xml.crypto.Data;
import javax.xml.transform.Result;
import java.sql.*;
import java.util.ArrayList;

public class OrderDA {
    private ArrayList<Order> orderList;
    private int recordCursor;

    public OrderDA() {
    }

    public ArrayList<Order> getOrderList() {
        populateOrderList();
        return orderList;
    }

    private void populateOrderList() {
        orderList = new ArrayList<Order>();

        try {
            PreparedStatement ps = DatabaseTools.GetConnection().prepareStatement("SELECT * FROM orders");
            ResultSet rs = ps.executeQuery();

            Order order;
            while(rs.next()) {
                order = new Order();
                order.setOrderID(rs.getInt("Order_ID"));
                order.setCustomerID(rs.getInt("Customer_ID"));
                order.setPaymentID(rs.getInt("Payment_ID"));
                order.setStatus(rs.getString("Status"));
                order.setOrder_date(rs.getDate("Order_Date"));
                orderList.add(order);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Order getOrder(int id) {
        Order order = new Order();
        try{
            PreparedStatement ps = DatabaseTools.GetConnection().prepareStatement("SELECT * from order where order_id = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                order.setOrderID(rs.getInt("Order_ID"));
                order.setCustomerID(rs.getInt("Customer_ID"));
                order.setPaymentID(rs.getInt("Payment_ID"));
                order.setStatus(rs.getString("Status"));
                order.setOrder_date(Date.valueOf(rs.getString("Order_Date")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return order;
    }

}
