package Controller;

import Database.DatabaseTools;
import Model.Product_Line;
import Model.ShippingData;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class ShipDA {

    private ArrayList<ShippingData> shipList;

    public ShipDA() {}

    public ArrayList<ShippingData> getShipList() {
        populateShippingList();
        return shipList;
    }

    private void populateShippingList() {
        shipList = new ArrayList<>();
        try {
            PreparedStatement ps = DatabaseTools.GetConnection().prepareStatement("SELECT * FROM shipping");
            ResultSet rs = ps.executeQuery();

            ShippingData shipData;
            while(rs.next()) {
                shipData = new ShippingData();
                shipData.setId(rs.getInt("Shipping_ID"));
                shipData.setOderId(rs.getInt("Order_ID"));
                shipData.setShipDate(rs.getDate("Shipping_Date"));
                shipData.setEstDate(rs.getDate("Expected_Arrival_Date"));
                shipData.setTracking(rs.getString("Tracking_Number"));
                shipData.setDelivered(rs.getBoolean("Delivered"));
                shipList.add(shipData);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Boolean shipIt(int id, String tracking, int orderID) {
        try {
            PreparedStatement ps = DatabaseTools.GetConnection().prepareStatement("UPDATE shipping set tracking_number = ? where shipping_Id = ?");

            ps.setString(1, tracking);
            ps.setInt(2, id);
            ps.executeUpdate();

            //After tracking updated we need to update the order status to SHIPPED
            ps = DatabaseTools.GetConnection().prepareStatement("UPDATE orders set Status = ? where order_id = ?");
            ps.setString(1, "SHIPPED");
            ps.setInt(2, orderID);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public Boolean delivered(int id, int orderID) {
        try {
            //After tracking updated we need to update the order status to SHIPPED
            PreparedStatement ps = DatabaseTools.GetConnection().prepareStatement("UPDATE orders set Status = ? where order_id = ?");
            ps.setString(1, "COMPLETED");
            ps.setInt(2, orderID);
            ps.executeUpdate();

            ps = DatabaseTools.GetConnection().prepareStatement("UPDATE shipping set delivered = true where shipping_id = ?");
            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
