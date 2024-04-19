package Controller;

import Database.DatabaseTools;
import Model.Supplier;

import java.sql.*;
import java.util.ArrayList;

public class SupplierDA {
    private ArrayList<Supplier> supList;

    public SupplierDA() {
        populateOrderList();
    }

    public ArrayList<Supplier> getSupList() {
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
}
