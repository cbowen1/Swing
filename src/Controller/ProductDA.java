package Controller;

import Database.DatabaseTools;
import Model.Product;

import java.sql.*;
import java.util.ArrayList;

public class ProductDA {
    private ArrayList<Product> productList;

    public ProductDA() {
        populateOrderList();
    }

    public ArrayList<Product> getOrderList() {
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
}
