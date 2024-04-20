package Model;

public class Inventory {
    private int id;
    private String name;
    private int qty;
    private int supplierID;

    public Inventory() {}
    public Inventory(String name, int qty, int supplierID) {
        this.name = name;
        this.qty = qty;
        this.supplierID = supplierID;
    }

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

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public int getSupplierID() {
        return supplierID;
    }

    public void setSupplierID(int supplierID) {
        this.supplierID = supplierID;
    }
}
