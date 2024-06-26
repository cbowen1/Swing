package Model;

public class Product {
    private int id;
    private int productLineId;
    private String name;
    private double unitPrice;
    private int qty;
    private double weight;

    public Product(String name, double price, int qty, int productLineId, double weight) {
        this.name = name;
        this.unitPrice = price;
        this.qty = qty;
        this.productLineId = productLineId;
        this.weight = weight;
    }

    public Product() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getProductLineId() {
        return productLineId;
    }

    public void setProductLineId(int productLineId) {
        this.productLineId = productLineId;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }
}
