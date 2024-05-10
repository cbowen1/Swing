package Controller;

import Database.DatabaseTools;
import Model.Inventory;
import Model.Product;

import javax.swing.*;
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

    public ArrayList<Product> getAvailableOrderList() {
        ArrayList<Product> availableItems = new ArrayList<>();
        try {
            PreparedStatement ps = DatabaseTools.GetConnection().prepareStatement("SELECT * FROM product where quantity > 0");
            ResultSet rs = ps.executeQuery();

            Product pro;
            while(rs.next()) {
                pro = new Product();
                pro.setId(rs.getInt("Product_ID"));
                pro.setProductLineId(rs.getInt("Product_Line_ID"));
                pro.setName(rs.getString("Product_Name"));
                pro.setUnitPrice(rs.getDouble("Unit_Price"));
                pro.setQty(rs.getInt("Quantity"));
                pro.setWeight(rs.getDouble("Weight"));
                availableItems.add(pro);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return availableItems;
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
                pro.setWeight(rs.getDouble("Weight"));
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

    public Double getProductWeight(int id) {
        Double weight = 0.0;
        try{
            PreparedStatement ps = DatabaseTools.GetConnection().prepareStatement("SELECT weight FROM product where product_id = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                weight = rs.getDouble("weight");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return weight;
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
                prod.setWeight(rs.getDouble("weight"));
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
                    "INSERT INTO product(product_id,product_line_id,product_name,unit_price,quantity,weight)values(?,?,?,?,?,?)"
            );
            ps.setInt(1, supID);
            ps.setInt(2, prod.getProductLineId());
            ps.setString(3, prod.getName());
            ps.setDouble(4, prod.getUnitPrice());
            ps.setInt(5, prod.getQty());
            ps.setDouble(6, prod.getWeight());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean updateProduct(Product prod) {
        try{
            PreparedStatement ps = DatabaseTools.GetConnection().prepareStatement(
                    "UPDATE product set product_name = ?, unit_price = ?, quantity = ?, weight = ? where product_id = ?");
            ps.setString(1, prod.getName());
            ps.setDouble(2, prod.getUnitPrice());
            ps.setInt(3, prod.getQty());
            ps.setDouble(4, prod.getWeight());
            ps.setInt(5, prod.getId());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public String removeProduct(int id) {
        try {
            int dialogResult = JOptionPane.showConfirmDialog (null, "Do you want to delete the product","Warning",JOptionPane.YES_NO_OPTION);
            if(dialogResult == JOptionPane.YES_OPTION){
                PreparedStatement ps = DatabaseTools.GetConnection().prepareStatement("DELETE FROM product where product_id = ?");
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
}
