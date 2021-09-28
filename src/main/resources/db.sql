create database if not exists cash_mashine;
use cash_mashine;

create table if not exists language (

 id INT PRIMARY KEY not null auto_increment,

 short_name VARCHAR(10) NOT NULL,

 full_name VARCHAR(25) NOT NULL

);

create table if not exists role(
 id INT PRIMARY KEY not null auto_increment,
 short_name VARCHAR(10) NOT NULL
);

create table if not exists role_language(

    role_id INT REFERENCES role(id) ON DELETE CASCADE,

	language_id INT REFERENCES language(id) ON DELETE CASCADE,
    role_name VARCHAR(45),

     PRIMARY KEY (role_id, language_id),

	UNIQUE (role_id, language_id),

    CONSTRAINT role_r_fk
        FOREIGN KEY role_r_fk (role_id) REFERENCES role (id)
        ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT language_fk
        FOREIGN KEY language_fk (language_id) REFERENCES language (id)
        ON DELETE CASCADE ON UPDATE CASCADE

);

CREATE TABLE if not exists user (

 id INT PRIMARY KEY not null auto_increment,

 login VARCHAR(20) UNIQUE NOT NULL,

 pass VARCHAR(20) NOT NULL,

 role_id INT NOT NULL,

 FOREIGN KEY (role_id) REFERENCES role(id) ON DELETE CASCADE
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

-- alter table receipt_product drop  foreign key receipt_fk;
-- alter table receipt_product drop  foreign key product_fk;
-- alter table role_language drop  foreign key role_r_fk;
-- alter table role_language drop  foreign key language_fk;




