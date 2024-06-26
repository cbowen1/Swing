USE Eclipse_Collectibles;

-- Drop and create `customer` table
DROP TABLE IF EXISTS customer;
CREATE TABLE customer (
    Customer_ID INT PRIMARY KEY,
    Customer_fName VARCHAR(50),
    Customer_lName VARCHAR(50),
    Email VARCHAR(100),
    Street_Address VARCHAR(100),
    City VARCHAR(50),
    State VARCHAR(2),
    ZipCode VARCHAR(5),
    IsActive BOOLEAN DEFAULT TRUE,
    FavoriteTeam VARCHAR(100)
);

-- Drop and create `payment_method` table
DROP TABLE IF EXISTS payment_method;
CREATE TABLE payment_method (
    Payment_ID INT PRIMARY KEY,
    Payment_Type VARCHAR(50),
    Payment_Amount DECIMAL(10, 2),
    Payment_Status VARCHAR(50)
);

-- Drop and create `orders` table
DROP TABLE IF EXISTS `order`;
CREATE TABLE `order` (
    Order_ID INT PRIMARY KEY,
    Customer_ID INT,
    Payment_ID INT,
    Status VARCHAR(50),
    Order_Date DATE,
    FOREIGN KEY (Customer_ID) REFERENCES customer(Customer_ID),
    FOREIGN KEY (Payment_ID) REFERENCES payment_method(Payment_ID)
);

-- Drop and create `supplier` table
DROP TABLE IF EXISTS supplier;
CREATE TABLE supplier (
    Supplier_ID INT PRIMARY KEY,
    Supplier_Name VARCHAR(100),
    Phone VARCHAR(20),
    Email VARCHAR(100),
    Address VARCHAR(255),
    Website VARCHAR(100)
);

-- Drop and create `product_line` table
DROP TABLE IF EXISTS product_line;
CREATE TABLE product_line (
    Product_LineID INT PRIMARY KEY,
    Product_Line_Name VARCHAR(100),
    Product_Description TEXT,
    Unit_Price DECIMAL(10, 2),
    Supplier_ID INT,
    FOREIGN KEY (Supplier_ID) REFERENCES supplier(Supplier_ID)
);

-- Drop and create `product` table
DROP TABLE IF EXISTS product;
CREATE TABLE product (
    Product_ID INT PRIMARY KEY,
    Product_Line_ID INT,
    Product_Name VARCHAR(100),
    Unit_Price DECIMAL(10, 2),
    Quantity INT,
    Weight DECIMAL(5,2),
    FOREIGN KEY (Product_Line_ID) REFERENCES product_line(Product_LineID)
);

-- Drop and create `supplies` table
DROP TABLE IF EXISTS supplies;
CREATE TABLE supplies (
    Inventory_ID INT PRIMARY KEY,
    Inventory_Name VARCHAR(100),
    Quantity INT,
    Supplier_ID INT,
    FOREIGN KEY (Supplier_ID) REFERENCES supplier(Supplier_ID)

);

DROP TABLE IF EXISTS `order_details`;
CREATE TABLE `order_details` (
     Order_ID INT,
     Product_ID INT,
     Quantity INT,
     FOREIGN KEY (Order_ID) REFERENCES `order`(Order_ID),
     FOREIGN KEY (Product_ID) REFERENCES product(Product_ID)
);

-- Drop and create `shipping` table
DROP TABLE IF EXISTS shipping;
CREATE TABLE shipping (
    Shipping_ID INT PRIMARY KEY,
    Order_ID INT,
    Shipping_Date DATE,
    Expected_Arrival_Date DATE,
    Tracking_Number VARCHAR(100),
    OrderWeight DECIMAL(5,2),
    Delivered BOOLEAN default FALSE,
    IsCancelled BOOLEAN default FALSE,
    FOREIGN KEY (Order_ID) REFERENCES `order`(Order_ID)
);

show tables;