package Controller;

import Database.DatabaseTools;
import Model.Inventory;

import java.sql.*;
import java.util.ArrayList;

public class InventoryDA {
    private ArrayList<Inventory> inventoryList;

    public InventoryDA() {
        populateOrderList();
    }

    public ArrayList<Inventory> getOrderList() {
        return inventoryList;
    }

    private void populateOrderList() {
        inventoryList = new ArrayList<Inventory>();

        try {
            PreparedStatement ps = DatabaseTools.GetConnection().prepareStatement("SELECT * FROM inventory");
            ResultSet rs = ps.executeQuery();

            Inventory inv;
            while(rs.next()) {
                inv = new Inventory();
                inv.setId(rs.getInt("Inventory_ID"));
                inv.setName(rs.getString("Inventory_Name"));
                inv.setQty(rs.getInt("Quantity"));
                inv.setSupplierID(rs.getInt("Supplier_ID"));
                inventoryList.add(inv);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
