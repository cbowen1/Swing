package Model;

public class Customer {
    private String customerID;
    private String customerName_first;
    private String customerName_last;

    public Customer(String customerID, String fName, String lName) {
        this.customerID = customerID;
        customerName_first = fName;
        customerName_last = lName;
    }

    public String getCustomerID() {
        return customerID;
    }

    public String getCustomerName_first() {
        return customerName_first;
    }

    public String getCustomerName_last() {
        return customerName_last;
    }
}
