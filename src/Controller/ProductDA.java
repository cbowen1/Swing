package Controller;

import Database.DatabaseTools;
import Model.Inventory;
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
        try {
            PreparedStatement ps = DatabaseTools.GetConnection().prepareStatement("SELECT * FROM product where product_id = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                prod.setId(rs.getInt("product_id"));
                prod.setProductLineId(rs.getInt("Product_line_id"));
                prod.setName(rs.getString("product_name"));
                prod.setUnitPrice(rs.getDouble("unit_price"));
                prod.setQty(rs.getInt("quantity"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return prod;
    }

    public boolean addProduct(Product prod) {
        try{
            PreparedStatement ps = DatabaseTools.GetConnection().prepareStatement("Select (max(product_id) + 1) as Product_ID from product");
            ResultSet rs = ps.executeQuery();
            int supID = 0;
            while(rs.next()) {
                supID = rs.getInt("Product_id");
            }
            ps = DatabaseTools.GetConnection().prepareStatement(
                    "INSERT INTO product(product_id,product_line_id,product_name,unit_price,quantity)values(?,?,?,?,?)"
            );
            ps.setInt(1, supID);
            ps.setInt(2, prod.getProductLineId());
            ps.setString(3, prod.getName());
            ps.setDouble(4, prod.getUnitPrice());
            ps.setInt(5, prod.getQty());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
