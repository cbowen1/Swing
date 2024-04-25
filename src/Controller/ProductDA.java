package Controller;

import Database.DatabaseTools;
import Model.Product;

import java.sql.*;
import java.util.ArrayList;

public class ProductDA {
    private ArrayList<Product> productList;

    public ProductDA() {
        //populateOrderList();
    }

    public ArrayList<Product> getOrderList() {
        populateOrderList();
        return productList;
    }

    private void populateOrderList() {
        productList = new ArrayList<Product>();

        try {
            PreparedStatement ps = DatabaseTools.GetConnection().prepareStatement("SELECT * FROM product");
            ResultSet rs = ps.executeQuery();

            Product pro;
            while(rs.next()) {
                pro = new Product();
                pro.setId(rs.getInt("Product_ID"));
                pro.setProductLineId(rs.getInt("Product_Line_ID"));
                pro.setName(rs.getString("Product_Name"));
                pro.setUnitPrice(rs.getDouble("Unit_Price"));
                pro.setQty(rs.getInt("Quantity"));
                productList.add(pro);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getProductName(int id) {
        String productName = null;
        try{
            PreparedStatement ps = DatabaseTools.GetConnection().prepareStatement("SELECT product_name FROM product where product_id = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                productName = rs.getString("product_name");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return productName;
    }
    public Double getProductPrice(int id) {
        Double price = 0.0;
        try{
            PreparedStatement ps = DatabaseTools.GetConnection().prepareStatement("SELECT unit_price FROM product where product_id = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                price = rs.getDouble("unit_price");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return price;
    }


    public Product getProduct(int id) {
        Product prod = new Product();
        return prod;
    }
}
