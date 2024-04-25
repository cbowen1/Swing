package Model;

import java.util.ArrayList;
import java.util.Date;

public class Order {
    private int orderID;
    private int customerID;
    private int paymentID;
    private String status;
    private Date order_date;

    private Customer customer;

    private ArrayList<Product> productList;

    public Order() {

    }
    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public int getPaymentID() {
        return paymentID;
    }

    public void setPaymentID(int paymentID) {
        this.paymentID = paymentID;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getOrder_date() {
        return order_date;
    }

    public void setOrder_date(Date order_date) {
        this.order_date = order_date;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public ArrayList<Product> getProductList() {
        return productList;
    }

    public void addProductToList(Product prod) {
        if(productList == null) {
            productList = new ArrayList<>();
        }

        productList.add(prod);
    }
}
