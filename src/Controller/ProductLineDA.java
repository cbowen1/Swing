package Controller;

import Database.DatabaseTools;
import Model.Product_Line;
import View.ProductLineUI;

import java.sql.*;
import java.util.ArrayList;

public class ProductLineDA {
    private ArrayList<Product_Line> plList;

    public ProductLineDA() {
    }

    public ArrayList<Product_Line> getProductLineList() {
        populateProductLineList();
        return plList;
    }

    private void populateProductLineList() {
        plList = new ArrayList<Product_Line>();

        try {
            PreparedStatement ps = DatabaseTools.GetConnection().prepareStatement("SELECT * FROM product_line");
            ResultSet rs = ps.executeQuery();

            Product_Line pl;
            while(rs.next()) {
                pl = new Product_Line();
                pl.setId(rs.getInt("Product_LineID"));
                pl.setName(rs.getString("Product_Line_Name"));
                pl.setDesc(rs.getString("Product_Description"));
                pl.setUnitPrice(rs.getDouble("Unit_Price"));
                pl.setSupplierId(rs.getInt("Supplier_ID"));
                plList.add(pl);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Product_Line getProdLine(int id) {
        Product_Line pl = new Product_Line();
        try {
            PreparedStatement ps = DatabaseTools.GetConnection().prepareStatement("SELECT * FROM product_line where product_lineID = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                pl.setId(rs.getInt("Product_LineID"));
                pl.setName(rs.getString("Product_Line_Name"));
                pl.setDesc(rs.getString("Product_Description"));
                pl.setUnitPrice(rs.getDouble("Unit_Price"));
                pl.setSupplierId(rs.getInt("Supplier_ID"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pl;
    }

    public boolean updateProductLine(Product_Line pl) {
        try{
            PreparedStatement ps = DatabaseTools.GetConnection().prepareStatement(
                    "UPDATE product_line set product_line_name = ?, product_description = ?, unit_price = ?, supplier_id =? where product_lineID = ?");
            ps.setString(5, String.valueOf(pl.getId()));
            ps.setString(1, pl.getName());
            ps.setString(2, String.valueOf(pl.getDesc()));
            ps.setString(3, String.valueOf(pl.getUnitPrice()));
            ps.setString(4, String.valueOf(pl.getSupplierId()));
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean addProductLine(Product_Line pl) {
        try{
            PreparedStatement ps = DatabaseTools.GetConnection().prepareStatement("Select (max(product_lineID) + 1) as PL_ID from product_line");
            ResultSet rs = ps.executeQuery();
            int newPLid = 0;
            while(rs.next()) { newPLid = rs.getInt("PL_ID"); }
            ps = DatabaseTools.GetConnection().prepareStatement(
                    "INSERT INTO product_line(product_lineID,product_line_name,product_description,unit_price,supplier_ID)value(?,?,?,?,?)"
            );
            ps.setInt(1, newPLid);
            ps.setString(2, pl.getName());
            ps.setString(3, pl.getDesc());
            ps.setDouble(4, pl.getUnitPrice());
            ps.setInt(4, pl.getSupplierId());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
