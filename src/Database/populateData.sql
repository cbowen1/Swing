USE Eclipse_Collectibles;

-- Inserting data into `customer`
INSERT INTO customer VALUES (1, 'Luisa', 'Jermy', 'ljermy0@uol.com.br', '0 Green Ridge Plaza', 'Saint Louis', 'MO', '63196');
INSERT INTO customer VALUES (2, 'Merl', 'Spracklin', 'mspracklin1@ezinearticles.com', '10 Aberg Place', 'Largo', 'FL', '34643');
INSERT INTO customer VALUES (3, 'Aldin', 'Malzard', 'amalzard2@wsj.com', '4567 Oxford Place', 'Winston Salem', 'NC', '27105');
INSERT INTO customer VALUES (4, 'Brander', 'Sewley', 'bsewley3@myspace.com', '6 Delladonna Pass', 'Tucson', 'AZ', '85748');
INSERT INTO customer VALUES (5, 'Prinz', 'Ten Broek', 'ptenbroek4@narod.ru', '9233 Mcguire Court', 'Dearborn', 'MI', '48126');
INSERT INTO customer VALUES (6, 'Alene', 'Knappen', 'aknappen5@geocities.jp', '18 Grim Parkway', 'San Bernardino', 'CA', '92410');
INSERT INTO customer VALUES (7, 'Anjela', 'Scotchmore', 'ascotchmore6@virginia.edu', '27295 Commercial Lane', 'Saint Louis', 'MO', '63136');
INSERT INTO customer VALUES (8, 'Tucky', 'Neljes', 'tneljes7@utexas.edu', '00 Bellgrove Trail', 'Littleton', 'CO', '80127');
INSERT INTO customer VALUES (9, 'Amandi', 'Hoston', 'ahoston8@godaddy.com', '88131 Michigan Street', 'Dallas', 'TX', '75387');
INSERT INTO customer VALUES (10, 'Sascha', 'Revans', 'srevans9@blogtalkradio.com', '6457 Golf Junction', 'York', 'PA', '17405');
INSERT INTO customer VALUES (11, 'Baxter', 'Cesconi', 'bcesconia@cnet.com', '615 Hallows Trail', 'San Jose', 'CA', '95118');
INSERT INTO customer VALUES (12, 'Allyn', 'Muller', 'amullerb@php.net', '29 Stang Plaza', 'Dallas', 'TX', '75353');
INSERT INTO customer VALUES (13, 'Reggie', 'McElvine', 'rmcelvinec@pcworld.com', '1622 7th Park', 'Hartford', 'CT', '06160');
INSERT INTO customer VALUES (14, 'Nanette', 'Dudding', 'nduddingd@squarespace.com', '3547 Eagan Alley', 'Hamilton', 'OH', '45020');
INSERT INTO customer VALUES (15, 'Arabella', 'Blazek', 'ablazeke@last.fm', '2913 Ohio Plaza', 'Columbus', 'GA', '31914');
INSERT INTO customer VALUES (16, 'Edita', 'Tuttiett', 'etuttiettf@un.org', '91872 Briar Crest Circle', 'East Saint Louis', 'IL', '62205');
INSERT INTO customer VALUES (17, 'Sharon', 'Burgill', 'sburgillg@wsj.com', '45670 Coleman Road', 'Portsmouth', 'NH', '00214');
INSERT INTO customer VALUES (18, 'Lin', 'Yakubovich', 'lyakubovichh@sfgate.com', '9 Buena Vista Parkway', 'Kansas City', 'KS', '66105');
INSERT INTO customer VALUES (19, 'Amalia', 'Edwicke', 'aedwickei@cmu.edu', '06 Londonderry Junction', 'Minneapolis', 'MN', '55480');
INSERT INTO customer VALUES (20, 'Paquito', 'Jollie', 'pjolliej@purevolume.com', '5 Esker Lane', 'Omaha', 'NE', '68124');
INSERT INTO customer VALUES (21, 'Roderigo', 'Ferbrache', 'rferbrachek@mayoclinic.com', '7606 Northwestern Lane', 'Peoria', 'IL', '61605');
INSERT INTO customer VALUES (22, 'Bryana', 'Light', 'blightl@drupal.org', '05 Sachtjen Place', 'Fayetteville', 'NC', '28314');
INSERT INTO customer VALUES (23, 'Arabelle', 'Strowan', 'astrowanm@cnn.com', '586 Bluejay Road', 'Los Angeles', 'CA', '90101');
INSERT INTO customer VALUES (24, 'Humberto', 'Heigold', 'hheigoldn@symantec.com', '8893 Nobel Avenue', 'Humble', 'TX', '77346');
INSERT INTO customer VALUES (25, 'Laird', 'Le Grice', 'llegriceo@stumbleupon.com', '19531 Melrose Crossing', 'Houston', 'TX', '77281');

-- Inserting data into `payment_method`
INSERT INTO payment_method VALUES 
(1, 'Credit Card', 100.00);

-- Inserting  data into `order`
INSERT INTO `order` VALUES 
(1, 1, 1, 'Processing', 2, CURDATE());

-- Inserting data into `product_line`
INSERT INTO product_line VALUES (1, '2022 Super Break Football Edition 10-Box Case', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit.', 849.95);
INSERT INTO product_line VALUES (2, '2023 Panini Donruss Optic Football Hobby H2 Box', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit.', 500.00);

-- Inserting data into `product`
INSERT INTO product VALUES (1, 1, '2022 Super Break Football Edition Box', 89.95, 12);

-- Inserting data into `inventory`
INSERT INTO inventory VALUES 
(1, 'Gadget', 100, 'Electronics');

-- Inserting data into `supplier`
INSERT INTO supplier VALUES 
(1, 'Gadget Supplier', '123-456-7890', 'supplier@email.com', '4567 Business Rd');

-- Inserting data into `shipping`
INSERT INTO shipping VALUES 
(1, 1, CURDATE(), DATE_ADD(CURDATE(), INTERVAL 5 DAY), 'TRACK123');
