create database if not exists cash_mashine;
use cash_mashine;

CREATE TABLE if not exists user (  

 id INT PRIMARY KEY not null auto_increment,  

 login VARCHAR(20) UNIQUE NOT NULL,
 
 pass VARCHAR(20) NOT NULL,
 
 role VARCHAR(30) NOT NULL

); 

CREATE TABLE if not exists product (  

 id INT PRIMARY KEY not null auto_increment,  

 name VARCHAR(20) NOT NULL UNIQUE,
 
 code VARCHAR(20) NOT NULL UNIQUE,
 
 capacityType VARCHAR(20) NOT NULL,
 
 capacity decimal(10, 2) NOT NULL,
 
 price decimal(10, 2) NOT NULL

); 

CREATE TABLE if not exists receipt (  
 id INT PRIMARY KEY not null auto_increment,
 
 date_of_creation DATETIME NOT NULL, 
 
 user_id INT NOT NULL,
 
 active boolean default true,
 
 FOREIGN KEY (user_id) REFERENCES user(id) ON DELETE CASCADE
);

CREATE TABLE if not exists receipt_product (  

 receipt_id INT ,  

 product_id INT ,  
 
 capacity decimal(10, 2) NOT NULL,
 
 price decimal(10, 2) NOT NULL,
 
 PRIMARY KEY (receipt_id, product_id),

 UNIQUE (receipt_id, product_id),
 
 CONSTRAINT receipt_fk
        FOREIGN KEY receipt_fk (receipt_id) REFERENCES receipt (id)
        ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT product_fk
        FOREIGN KEY product_fk (product_id) REFERENCES product (id)
        ON DELETE CASCADE ON UPDATE CASCADE

);
create table if not exists language (

id INT PRIMARY KEY not null auto_increment,

short_name VARCHAR(10) NOT NULL,

full_name VARCHAR(25) NOT NULL

);

create table if not exists product_language(

    product_id INT REFERENCES product(id) ON DELETE CASCADE,  

	language_id INT REFERENCES language(id) ON DELETE CASCADE,
    product_name VARCHAR(45),
    
     PRIMARY KEY (product_id, language_id),

	UNIQUE (product_id, language_id),
    
    CONSTRAINT product_r_fk
        FOREIGN KEY product_r_fk (product_id) REFERENCES product (id)
        ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT language_fk
        FOREIGN KEY language_fk (language_id) REFERENCES language (id)
        ON DELETE CASCADE ON UPDATE CASCADE

);