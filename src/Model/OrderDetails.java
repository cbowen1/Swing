package Model;

public class OrderDetails {
    private int productID;
    private int qty;

    public OrderDetails(String id, int productID, int qty) {
        this.productID = productID;
        this.qty = qty;
    }

    public OrderDetails() {}

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }
}
