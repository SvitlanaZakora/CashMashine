create database if not exists cash_mashine_test;

create table if not exists language (
 id INT PRIMARY KEY not null auto_increment,
 short_name VARCHAR(10) NOT NULL,
 full_name VARCHAR(25) NOT NULL
);

insert into language(id, short_name, full_name) values(1, "en", "en"),(2,"ua","ua");

create table if not exists role(
 id INT PRIMARY KEY not null auto_increment,
 short_name VARCHAR(10) NOT NULL
);

insert into role(id, short_name) values(1, "C"),(2,"SC"),(3,"CE");

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

insert into role_language(role_id, language_id, role_name) values(1, 1, "CASHIER"),(1, 2,"КАСИР"),(2, 1,"Senior"),(2, 2,"Старш касир"),(3, 1,"Expert"),(3, 2,"Товарознавець");

CREATE TABLE if not exists user (
 id INT PRIMARY KEY not null auto_increment,
 login VARCHAR(20) UNIQUE NOT NULL,
 pass VARCHAR(20) NOT NULL,
 role_id INT NOT NULL,
 FOREIGN KEY (role_id) REFERENCES role(id) ON DELETE CASCADE
);

insert into user(id, login, pass, role_id) values(1, "fir", "fir", 1),(2,"sec", "sec",2),(3,"thir","thir", 3);

CREATE TABLE if not exists product (
 id INT PRIMARY KEY not null auto_increment,
 name VARCHAR(20) NOT NULL UNIQUE,
 code VARCHAR(20) NOT NULL UNIQUE,
 capacityType VARCHAR(20) NOT NULL,
 capacity decimal(10, 2) NOT NULL,
 price decimal(10, 2) NOT NULL
);

insert into product(id, name, code, capacityType, capacity, price) values(1, "tomato", "111", "kg", 20.1, 10),(2,"milk", "222", "psc", 10, 20);

CREATE TABLE if not exists receipt (
 id INT PRIMARY KEY not null auto_increment,
 date_of_creation DATETIME NOT NULL,
 user_id INT NOT NULL,
 total decimal(10, 2) DEFAULT 0.00,
 active boolean default true,
 FOREIGN KEY (user_id) REFERENCES user(id) ON DELETE CASCADE
);

insert into receipt(id, date_of_creation, user_id, total, active) values(1, "2021-10-02 21:50:05", 1, 20, 0),(2, "2021-10-02 21:50:05", 1, 20, 1);

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

insert into receipt_product(receipt_id, product_id, capacity, price) values(1, 1, 2, 20);