package Model;

public class Customer {
    private int customerID;
    private String customerName_first;
    private String customerName_last;
    private String email;
    private String street_address;
    private String city;
    private String state;
    private String zip;
    private Boolean isActive;

    public Customer(String fName, String lName, String email, String street, String city, String state, String zip) {
        this.customerName_first = fName;
        this.customerName_last = lName;
        this.email = email;
        this.street_address = street;
        this.city = city;
        this.state = state;
        this.zip = zip;
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public String getCustomerName_first() {
        return customerName_first;
    }

    public void setCustomerName_first(String customerName_first) {
        this.customerName_first = customerName_first;
    }

    public String getCustomerName_last() {
        return customerName_last;
    }

    public void setCustomerName_last(String customerName_last) {
        this.customerName_last = customerName_last;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStreet_address() {
        return street_address;
    }

    public void setStreet_address(String street_address) {
        this.street_address = street_address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public String getFullAddress() {
        return street_address + "," + city + " " + state + " " + zip;
    }
    public Customer() { }

}