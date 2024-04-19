package Controller;

import Database.DatabaseTools;
import Model.Product_Line;

import java.sql.*;
import java.util.ArrayList;

public class ProductLineDA {
    private ArrayList<Product_Line> plList;

    public ProductLineDA() {
        populateOrderList();
    }

    public ArrayList<Product_Line> getProductLineList() {
        return plList;
    }

    private void populateOrderList() {
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
}
