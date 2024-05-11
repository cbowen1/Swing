USE Eclipse_Collectibles;

-- Inserting data into `customer`
INSERT INTO customer VALUES (1, 'Luisa', 'Jermy', 'ljermy0@uol.com.br', '0 Green Ridge Plaza', 'Saint Louis', 'MO', '63196', TRUE, "Steelers");
INSERT INTO customer VALUES (2, 'Merl', 'Spracklin', 'mspracklin1@ezinearticles.com', '10 Aberg Place', 'Largo', 'FL', '34643', TRUE,"");
INSERT INTO customer VALUES (3, 'Aldin', 'Malzard', 'amalzard2@wsj.com', '4567 Oxford Place', 'Winston Salem', 'NC', '27105', TRUE,"");
INSERT INTO customer VALUES (4, 'Brander', 'Sewley', 'bsewley3@myspace.com', '6 Delladonna Pass', 'Tucson', 'AZ', '85748', FALSE,"");
INSERT INTO customer VALUES (5, 'Prinz', 'Ten Broek', 'ptenbroek4@narod.ru', '9233 Mcguire Court', 'Dearborn', 'MI', '48126', TRUE,"");
INSERT INTO customer VALUES (6, 'Alene', 'Knappen', 'aknappen5@geocities.jp', '18 Grim Parkway', 'San Bernardino', 'CA', '92410', TRUE,"");
INSERT INTO customer VALUES (7, 'Anjela', 'Scotchmore', 'ascotchmore6@virginia.edu', '27295 Commercial Lane', 'Saint Louis', 'MO', '63136', TRUE,"");
INSERT INTO customer VALUES (8, 'Tucky', 'Neljes', 'tneljes7@utexas.edu', '00 Bellgrove Trail', 'Littleton', 'CO', '80127', TRUE, "Eagles");
INSERT INTO customer VALUES (9, 'Amandi', 'Hoston', 'ahoston8@godaddy.com', '88131 Michigan Street', 'Dallas', 'TX', '75387', TRUE,"");
INSERT INTO customer VALUES (10, 'Sascha', 'Revans', 'srevans9@blogtalkradio.com', '6457 Golf Junction', 'York', 'PA', '17405', TRUE,"");
INSERT INTO customer VALUES (11, 'Baxter', 'Cesconi', 'bcesconia@cnet.com', '615 Hallows Trail', 'San Jose', 'CA', '95118', TRUE,"");
INSERT INTO customer VALUES (12, 'Allyn', 'Muller', 'amullerb@php.net', '29 Stang Plaza', 'Dallas', 'TX', '75353', TRUE,"");
INSERT INTO customer VALUES (13, 'Reggie', 'McElvine', 'rmcelvinec@pcworld.com', '1622 7th Park', 'Hartford', 'CT', '06160', TRUE,"");
INSERT INTO customer VALUES (14, 'Nanette', 'Dudding', 'nduddingd@squarespace.com', '3547 Eagan Alley', 'Hamilton', 'OH', '45020', TRUE,"");
INSERT INTO customer VALUES (15, 'Arabella', 'Blazek', 'ablazeke@last.fm', '2913 Ohio Plaza', 'Columbus', 'GA', '31914', TRUE,"");
INSERT INTO customer VALUES (16, 'Edita', 'Tuttiett', 'etuttiettf@un.org', '91872 Briar Crest Circle', 'East Saint Louis', 'IL', '62205', TRUE,"");
INSERT INTO customer VALUES (17, 'Sharon', 'Burgill', 'sburgillg@wsj.com', '45670 Coleman Road', 'Portsmouth', 'NH', '00214', TRUE,"");
INSERT INTO customer VALUES (18, 'Lin', 'Yakubovich', 'lyakubovichh@sfgate.com', '9 Buena Vista Parkway', 'Kansas City', 'KS', '66105', TRUE,"");
INSERT INTO customer VALUES (19, 'Amalia', 'Edwicke', 'aedwickei@cmu.edu', '06 Londonderry Junction', 'Minneapolis', 'MN', '55480', TRUE,"");
INSERT INTO customer VALUES (20, 'Paquito', 'Jollie', 'pjolliej@purevolume.com', '5 Esker Lane', 'Omaha', 'NE', '68124', FALSE,"");
INSERT INTO customer VALUES (21, 'Roderigo', 'Ferbrache', 'rferbrachek@mayoclinic.com', '7606 Northwestern Lane', 'Peoria', 'IL', '61605', TRUE,"");
INSERT INTO customer VALUES (22, 'Bryana', 'Light', 'blightl@drupal.org', '05 Sachtjen Place', 'Fayetteville', 'NC', '28314', TRUE,"");
INSERT INTO customer VALUES (23, 'Arabelle', 'Strowan', 'astrowanm@cnn.com', '586 Bluejay Road', 'Los Angeles', 'CA', '90101', TRUE,"");
INSERT INTO customer VALUES (24, 'Humberto', 'Heigold', 'hheigoldn@symantec.com', '8893 Nobel Avenue', 'Humble', 'TX', '77346', TRUE,"");
INSERT INTO customer VALUES (25, 'Laird', 'Le Grice', 'llegriceo@stumbleupon.com', '19531 Melrose Crossing', 'Houston', 'TX', '77281', TRUE,"");

-- Inserting data into `payment_method`
INSERT INTO payment_method VALUES (1, 'Credit Card', 234.16, "PAID");
INSERT INTO payment_method VALUES (2, 'Paypal', 17.26, "PAID");

-- Inserting data into `supplier`
INSERT INTO supplier VALUES (1, 'Amazon.com', NULL, NULL, NULL, 'www.amazon.com');
INSERT INTO supplier VALUES (2, 'Steel City Collectibles', NULL, NULL, NULL, 'www.steelcitycollectibles.com');
INSERT INTO supplier VALUES (3, 'Panini', NULL, NULL, NULL, 'www.paniniamerica.net');
INSERT INTO supplier VALUES (4, 'Random Supply Co', '410-867-5309', 'randomPerson@randomsupplyco.com','123 Main St, Havre de Grace,MD', 'www.notawebsite.com');
INSERT INTO supplier VALUES (5, 'Dave & Adams Cardworld', '716-626-0000', NULL, '8075 Sheridan Dr, Williamsville, NY 14221', 'https://www.dacardworld.com/');
INSERT INTO supplier VALUES (6, 'Blowout Cards', NULL, NULL, NULL, NULL);

-- Inserting  data into `orders`
INSERT INTO `order` VALUES (1, 13, null, 'Completed', '2024-03-14');
INSERT INTO `order` VALUES (2, 4, 2, 'Shipped', '2024-04-18');

-- Inserting data into `product_line`
INSERT INTO product_line VALUES (1, '2022 Super Break Football Edition 10-Box Case', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit.', 849.95,2);
INSERT INTO product_line VALUES (2, '2023 Panini Donruss Optic Football Hobby H2 Box', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit.', 500.00,3);
INSERT INTO product_line VALUES (3, '2024 Gold Rush Autographed Full-Size Authentic Football Helmet 4-Box Case', '1 autographed and authenticated full-size football helmet per box', 1024.95,2);
INSERT INTO product_line VALUES (4, '2023 Panini Obsidian Football 10-Box Case', NULL, 749.99,5);
INSERT INTO product_line VALUES (5, '2023 Panini Obsidian Football FOTL Hobby 12-Box Case', NULL, 8199.95, 5);
INSERT INTO product_line VALUES (6, '2024 Sage High Series Football Hobby 16-Box Case', NULL, 1849.95, 5);

-- Inserting data into `product`
INSERT INTO product VALUES (1, 1, '2022 Super Break Football Edition Box', 89.95, 121, 1.25);
INSERT INTO product VALUES (2, 3, '2024 Gold Rush Autographed Full-Size Helmet',499.95,27, 6.40);
INSERT INTO product VALUES (3, 6, '2024 Sage High Series Football Hobby Box', 119.95, 16, .75);

-- Inserting data into `supplies`
INSERT INTO supplies VALUES (1, 'Penny Sleeves', 843, 1);
INSERT INTO supplies VALUES (2, 'Top Loaders', 341,  4);
INSERT INTO supplies VALUES (3, 'Bubble Mailers', 100, 4);

-- Inserting data into `shipping`
INSERT INTO shipping VALUES 
(1, 2, '2024-04-18', DATE_ADD(CURDATE(), INTERVAL 5 DAY), 'TRACK123',1.25, false, false);

INSERT INTO order_details VALUES (1,1,2);
INSERT INTO order_details VALUES (1,2,2);
INSERT INTO order_details VALUES (2,3,1);
