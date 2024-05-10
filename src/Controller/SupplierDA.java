package Controller;

import Database.DatabaseTools;
import Model.Supplier;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.ArrayList;

public class SupplierDA {
    private ArrayList<Supplier> supList;
    Component parent;

    public SupplierDA() {
        //populateOrderList();
    }

    public void setParent(Component parent) {
        this.parent = parent;
    }
    public ArrayList<Supplier> getSupList() {
        populateOrderList();
        return supList;
    }

    private void populateOrderList() {
        supList = new ArrayList<Supplier>();

        try {
            PreparedStatement ps = DatabaseTools.GetConnection().prepareStatement("SELECT * FROM supplier");
            ResultSet rs = ps.executeQuery();

            Supplier sup;
            while(rs.next()) {
                sup = new Supplier();
                sup.setId(rs.getInt("Supplier_ID"));
                sup.setName(rs.getString("Supplier_Name"));
                sup.setPhone(rs.getString("Phone"));
                sup.setEmail(rs.getString("Email"));
                sup.setAddress(rs.getString("Address"));
                sup.setWebsite(rs.getString("Website"));
                supList.add(sup);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Supplier getSupplier(int id) {
        Supplier sup = new Supplier();
        try{
            PreparedStatement ps = DatabaseTools.GetConnection().prepareStatement("SELECT * FROM supplier where supplier_id = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                sup.setId(rs.getInt("Supplier_ID"));
                sup.setName(rs.getString("Supplier_Name"));
                sup.setPhone(rs.getString("Phone"));
                sup.setEmail(rs.getString("Email"));
                sup.setAddress(rs.getString("Address"));
                sup.setWebsite(rs.getString("Website"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return sup;
    }

    public boolean addSupplier(Supplier sup) {
        try{
            PreparedStatement ps = DatabaseTools.GetConnection().prepareStatement("Select (max(supplier_id) + 1) as Supplier_ID from supplier");
            ResultSet rs = ps.executeQuery();
            int supID = 0;
            while(rs.next()) {
                supID = rs.getInt("Supplier_ID");
            }
            ps = DatabaseTools.GetConnection().prepareStatement(
                    "INSERT INTO supplier(supplier_id,supplier_name,phone,email,address,website)values(?,?,?,?,?,?)"
            );
            ps.setString(1, String.valueOf(supID));
            ps.setString(2, sup.getName());
            ps.setString(3, sup.getPhone());
            ps.setString(4, sup.getEmail());
            ps.setString(5, sup.getAddress());
            ps.setString(6, sup.getWebsite());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public String removeSupplier(int id) {
        try {
            int dialogResult = JOptionPane.showConfirmDialog (parent, "Do you want to Delete the supplier","Warning",JOptionPane.YES_NO_OPTION);
            if(dialogResult == JOptionPane.YES_OPTION){
                PreparedStatement ps = DatabaseTools.GetConnection().prepareStatement("DELETE FROM supplier where supplier_id = ?");
                ps.setInt(1, id);
                ps.executeUpdate();
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "false";
        }
        return "true";
    }

    public boolean updateSupplier(Supplier sup) {
        try{
            PreparedStatement ps = DatabaseTools.GetConnection().prepareStatement(
                    "UPDATE supplier set supplier_name = ?, phone = ?, email = ?, address = ?, website = ? where supplier_id = ?");
            ps.setString(6, String.valueOf(sup.getId()));
            ps.setString(1, sup.getName());
            ps.setString(2, sup.getPhone());
            ps.setString(3, sup.getEmail());
            ps.setString(4, sup.getAddress());
            ps.setString(5, sup.getWebsite());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
