USE Eclipse_Collectibles;

-- Inserting data into `customer`
INSERT INTO customer VALUES 
(1, 'John', 'Doe', 'john.adam@email.com', '1234 Main St', 'Baltimore', 'MD', '21001');

-- Inserting data into `payment_method`
INSERT INTO payment_method VALUES 
(1, 'Credit Card', 100.00);

-- Inserting  data into `order`
INSERT INTO `order` VALUES 
(1, 1, 1, 'Processing', 2, CURDATE());

-- Inserting data into `product`
INSERT INTO product VALUES 
(1, 'Gadget', 49.99, 'Electronics');

-- Inserting data into `product_line`
INSERT INTO product_line VALUES 
(1, 'Electronics', 'High-quality electronic gadgets', 49.99);

-- Inserting data into `inventory`
INSERT INTO inventory VALUES 
(1, 'Gadget', 100, 'Electronics');

-- Inserting data into `supplier`
INSERT INTO supplier VALUES 
(1, 'Gadget Supplier', '123-456-7890', 'supplier@email.com', '4567 Business Rd');

-- Inserting data into `shipping`
INSERT INTO shipping VALUES 
(1, 1, CURDATE(), DATE_ADD(CURDATE(), INTERVAL 5 DAY), 'TRACK123');
