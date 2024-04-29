package Controller;

import Database.DatabaseTools;
import Model.Order;
import Model.OrderDetails;
import Model.Product;
import com.mysql.cj.x.protobuf.MysqlxCrud;

import javax.swing.*;
import javax.xml.crypto.Data;
import javax.xml.transform.Result;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;

public class OrderDA {
    private ArrayList<Order> orderList;
    private ArrayList<OrderDetails> orderDetails;
    private int recordCursor;

    public OrderDA() {
    }

    public ArrayList<Order> getOrderList() {
        populateOrderList();
        return orderList;
    }

    public ArrayList<OrderDetails> getOrderDetails(int orderID) {
        orderDetails = new ArrayList<>();
        try{
            PreparedStatement ps = DatabaseTools.GetConnection().prepareStatement("SELECT * FROM order_details where Order_ID = ?");
            ps.setInt(1, orderID);
            ResultSet rs = ps.executeQuery();

            OrderDetails od;
            while(rs.next()) {
                od = new OrderDetails();
                od.setProductID(rs.getInt("Product_ID"));
                od.setQty(rs.getInt("Quantity"));
                orderDetails.add(od);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return orderDetails;
    }

    private void populateOrderList() {
        orderList = new ArrayList<>();

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
            PreparedStatement ps = DatabaseTools.GetConnection().prepareStatement("SELECT * from orders where order_id = ?");
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

    public boolean addOrder(Order o, ArrayList<OrderDetails> od) {
        //TODO: Most of these queries should be handled by TRIGGERS in the database to create rows
        PreparedStatement ps;
        int newOrderID = 0;
        int shippingID = 0;
        int paymentID = 0;

        try{
            ps = DatabaseTools.GetConnection().prepareStatement("Select (max(Payment_Id) + 1) as Payment_id from payment_method");
            ResultSet rs = ps.executeQuery();
            while(rs.next()) { paymentID = rs.getInt("Payment_id"); }
            ps = DatabaseTools.GetConnection().prepareStatement(
                    "INSERT INTO Payment_Method(payment_id,payment_type,Payment_amount)value(?,?,?)"
            );
            ps.setInt(1,paymentID);
            ps.setString(2,"Online Payment");
            ps.setInt(3,0);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        try{
            ps = DatabaseTools.GetConnection().prepareStatement("Select (max(Order_ID) + 1) as Order_Id from orders");
            ResultSet rs = ps.executeQuery();
            while(rs.next()) { newOrderID = rs.getInt("Order_Id"); }
            ps = DatabaseTools.GetConnection().prepareStatement(
                    "INSERT INTO orders(order_id,customer_id,payment_id,status,order_date)value(?,?,?,?,?)"
            );
            ps.setInt(1,newOrderID);
            ps.setInt(2,o.getCustomerID());
            ps.setInt(3, paymentID);
            ps.setString(4, "NEW");
            java.sql.Date dt = new Date(o.getOrder_date().getTime());
            ps.setDate(5, dt);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        double orderPrice = 0;
        ProductDA pda = new ProductDA();
        for(OrderDetails odt : od) {
            try {
                orderPrice += odt.getQty() * (pda.getProductPrice(odt.getProductID()));
                ps = DatabaseTools.GetConnection().prepareStatement(
                        "INSERT INTO order_details(order_id,product_id,quantity)value(?,?,?)"
                );
                ps.setInt(1,newOrderID);
                ps.setInt(2,odt.getProductID());
                ps.setInt(3,odt.getQty());
                ps.executeUpdate();

                ps = DatabaseTools.GetConnection().prepareStatement(
                        "UPDATE product set quantity = quantity - ? where product_id = ?"
                );
                ps.setInt(1,odt.getQty());
                ps.setInt(2,odt.getProductID());
                ps.executeUpdate();

            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }

        try{
            ps = DatabaseTools.GetConnection().prepareStatement("UPDATE payment_method set payment_amount = ? where payment_id = ?");
            ps.setDouble(1,orderPrice);
            ps.setInt(2,paymentID);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        try{
            ps = DatabaseTools.GetConnection().prepareStatement("Select (max(Shipping_ID) + 1) as shipping_id from shipping");
            ResultSet rs = ps.executeQuery();
            while(rs.next()) { shippingID = rs.getInt("shipping_id"); }

            ps = DatabaseTools.GetConnection().prepareStatement(
                    "INSERT INTO shipping(shipping_id,order_id,shipping_date,expected_arrival_date,tracking_number)value(?,?,?,?,?)"
            );
            ps.setInt(1,shippingID);
            ps.setInt(2,newOrderID);
            Calendar calendar = Calendar.getInstance();
            java.sql.Date sqlDate = new Date(calendar.getTime().getTime());
            calendar.add(Calendar.DATE, 4);
            java.sql.Date newDate = new Date(calendar.getTime().getTime());

            ps.setDate(3, sqlDate);
            ps.setDate(4, newDate);
            ps.setString(5,null);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean removeOrder(int id) {
        //Set order to cancelled
        //Update shipping status to cancelled
        //Update payment to cancelled
        //Update inventory to show new product

        try{
            //TODO: Find all showConfirmDialog and see how CANCEL returns, should NOT be success/error. but just return
            int dialogResult = JOptionPane.showConfirmDialog (null, "Do you want to cancel this order?","Warning",JOptionPane.YES_NO_OPTION);
            if(dialogResult == JOptionPane.YES_OPTION){
                PreparedStatement ps = DatabaseTools.GetConnection().prepareStatement("UPDATE order set STATUS = ? where order_id = ?");
                ps.setString(1, "CANCELLED");
                ps.setInt(2, id);
                //ps.executeUpdate();

                ps = DatabaseTools.GetConnection().prepareStatement("UPDATE shipping set isCancelled = ? where shipping_Id = ?");

                ps.setBoolean(1, true);
                ps.setInt(2, id);
                ps.executeUpdate();


            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
