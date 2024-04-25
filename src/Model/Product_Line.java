package Model;

public class Product_Line {
    private int id;
    private String name;
    private String desc;
    private double unitPrice;

    private int supplierId;

    public Product_Line(String name, String desc, double price, int id) {
        this.name = name;
        this.desc = desc;
        this.unitPrice =  price;
        this.supplierId = id;
    }

    public Product_Line() {}

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

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(int supplierId) {
        this.supplierId = supplierId;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
