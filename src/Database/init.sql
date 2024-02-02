CREATE DATABASE EXAMPLE;

CREATE TABLE businessTest (
    id int NOT NULL,
    symbol varchar(4) NOT NULL,
    companyName varchar(255) NOT NULL,
    price double NOT NULL
);

INSERT INTO businessTest (id, symbol, companyName, price) VALUES (1, "BAC", "Bank of Cale", 14.99);