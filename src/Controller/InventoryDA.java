package Controller;

import Database.DatabaseTools;
import Model.Inventory;

import java.sql.*;
import java.util.ArrayList;

public class InventoryDA {
    private ArrayList<Inventory> inventoryList;

    public InventoryDA() {
        //populateOrderList();
    }

    public ArrayList<Inventory> getOrderList() {
        populateOrderList();
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

    public Inventory getInventory(int id) {
        Inventory inv = new Inventory();
        try {
            PreparedStatement ps = DatabaseTools.GetConnection().prepareStatement("SELECT * FROM inventory where inventory_id = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                inv.setId(rs.getInt("Inventory_ID"));
                inv.setName(rs.getString("Inventory_Name"));
                inv.setQty(rs.getInt("Quantity"));
                inv.setSupplierID(rs.getInt("Supplier_ID"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return inv;
    }

    public boolean updateInventory(Inventory inv) {
        try{
            PreparedStatement ps = DatabaseTools.GetConnection().prepareStatement(
                    "UPDATE inventory set inventory_name = ?, quantity = ?, supplier_id = ? where inventory_id = ?");
            ps.setString(4, String.valueOf(inv.getId()));
            ps.setString(1, inv.getName());
            ps.setString(2, String.valueOf(inv.getQty()));
            ps.setString(3, String.valueOf(inv.getSupplierID()));
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean addInventory(Inventory inv) {
        try{
            PreparedStatement ps = DatabaseTools.GetConnection().prepareStatement("Select (max(inventory_id) + 1) as Supplier_ID from inventory");
            ResultSet rs = ps.executeQuery();
            int newInvID = 0;
            while(rs.next()) { newInvID = rs.getInt("Supplier_ID"); }
            ps = DatabaseTools.GetConnection().prepareStatement(
                    "INSERT INTO inventory(inventory_id,inventory_name,quantity,supplier_id)value(?,?,?,?)"
            );
            ps.setInt(1, newInvID);
            ps.setString(2, inv.getName());
            ps.setInt(3, inv.getQty());
            ps.setInt(4, inv.getSupplierID());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
