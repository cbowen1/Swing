

-- Check if the database exists
SELECT SCHEMA_NAME
FROM INFORMATION_SCHEMA.SCHEMATA
WHERE SCHEMA_NAME = 'Eclipse_Collectibles';

-- If the database exists, drop it
DROP DATABASE IF EXISTS Eclipse_Collectibles;

-- Create a new database
CREATE DATABASE Eclipse_Collectibles;

show databases;
