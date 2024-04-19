package Controller;

import Database.DatabaseTools;
import Model.Order;

import java.sql.*;
import java.util.ArrayList;

public class OrderDA {
    private ArrayList<Order> orderList;
    private int recordCursor;

    public OrderDA() {
        setRecordCursorAtStart();
        populateOrderList();
    }

    public ArrayList<Order> getOrderList() {
        return orderList;
    }

    private void populateOrderList() {
        orderList = new ArrayList<Order>();

        try {
            PreparedStatement ps = DatabaseTools.GetConnection().prepareStatement("SELECT * FROM order");
            ResultSet rs = ps.executeQuery();

            Order order;
            while(rs.next()) {
                order = new Order();
                order.setOrderID(rs.getInt("Order_ID"));
                order.setCustomerID(rs.getInt("Customer_ID"));
                order.setPaymentID(rs.getInt("Payment_ID"));
                order.setStatus(rs.getString("Status"));
                order.setQuantity(rs.getInt("Quantity"));
                order.setOrder_date(rs.getDate("Order_Date"));
                orderList.add(order);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setRecordCursorAtStart() {
        recordCursor = 0;
    }


    public Order getNextOrder() {
        recordCursor = recordCursor >= orderList.size() -1 ? orderList.size()-1:recordCursor++;
        return orderList.get(recordCursor);
    }
}
