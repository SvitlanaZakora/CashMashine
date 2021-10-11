package util;

public class SQLConstants {

    public static final String INSERT_USER = "insert into user (login,pass,role_id) values (?,?,?)";
    public static final String SELECT_USER_BY_LOGIN_WITH_LANG = "select u.*, r.short_name, rl.role_name from user u join role r on u.role_id = r.id join role_language rl on r.id = rl.role_id join language l on rl.language_id = l.id where u.login = ? and l.short_name = ?";
    public static final String FIELD_USER_ID = "id";
    public static final String FIELD_USER_LOGIN = "login";
    public static final String FIELD_USER_PASS = "pass";
    public static final String SELECT_USER_BY_ID_WITH_LANG = "select u.*, r.short_name, rl.role_name from user u join role r on u.role_id = r.id join role_language rl on r.id = rl.role_id join language l on rl.language_id = l.id where u.id = ?  and l.short_name = ?";

    public static final String FIELD_ROLE_ID = "role_id";
    public static final String FIELD_ROLE_SHORT_NAME = "short_name";
    public static final String FIELD_ROLE_NAME = "role_name";

    public static final String SELECT_ALL_ROLES_BY_LANG = "select rl.role_id, rl.role_name, r.short_name from role_language rl join language l on rl.language_id = l.id join role r on r.id = rl.role_id where l.short_name = ?";
    public static final String SELECT_ROLE_BY_NAME = "select r.* from role_language rl join role r on rl.role_id = r.id where role_name = ?";

    public static final String INSERT_PRODUCT = "insert into product (name,code,capacityType,capacity,price) values (?,?,?,?,?)";
    public static final String SELECT_PRODUCT_BY_NAME = "select * from product where name = ?";
    public static final String SELECT_PRODUCT_BY_CODE = "select * from product where code = ?";
    public static final String FIELD_PRODUCT_ID = "id";
    public static final String FIELD_RECEIPT_PRODUCT_ID = "product_id";
    public static final String FIELD_RECEIPT_PRODUCT_CAPACITY = "capacity";
    public static final String FIELD_RECEIPT_PRODUCT_PRICE = "price";
    public static final String FIELD_PRODUCT_NAME = "name";
    public static final String FIELD_PRODUCT_CAPACITY_TYPE = "capacityType";
    public static final String FIELD_PRODUCT_CAPACITY = "capacity";
    public static final String FIELD_PRODUCT_PRICE = "price";
    public static final String FIELD_PRODUCT_CODE = "code";
    public static final String SELECT_ALL_FROM_PRODUCT = "select * from product";
    public static final String COUNT_ALL_PRODUCTS = "SELECT COUNT(*) FROM product";
    public static final String COUNT_ALL_PRODUCTS_BY_NAME = "SELECT COUNT(*) FROM product WHERE name like ?";
    public static final String COUNT_ALL_PRODUCTS_BY_CODE = "SELECT COUNT(*) FROM product WHERE code like ?";
    public static final String SELECT_PAGE_FROM_PRODUCT = "select * from product LIMIT ?, ?";
    public static final String SELECT_PAGE_PRODUCTS_BY_CODE = "select * from product where code LIKE ? LIMIT ?, ?";
    public static final String SELECT_PAGE_PRODUCTS_BY_NAME = "select * from product where name LIKE ? LIMIT ?, ?";
    public static final String UPDATE_PRODUCT = "UPDATE product SET name = ?, code = ?, capacityType = ?, capacity = ?, price = ? where id = ?";
    public static final String DELETE_PRODUCT = "delete from product where product.id = ?";
    public static final String SELECT_PRODUCT_BY_ID = "select * from product where id = ?";

    public static final String INSERT_RECEIPT = "insert into receipt (date_of_creation,user_id) values (?,?)";
    public static final String SELECT_RECEIPT_BY_ID = "select * from receipt where id = ?";
    public static final String SELECT_ALL_FROM_RECEIPT_BY_ACTIVE_AND_CREATION_DATE = "select * from receipt where active = ? and date_of_creation between ? and ?";
    public static final String SELECT_RECEIPT_BY_ID_WITH_OWNER_NAME = "select receipt.id, receipt.date_of_creation, receipt.user_id, user.login from receipt " +
            "join user on user.id = receipt.user_id where receipt.id = ?";
    public static final String SELECT_PRODUCTS_BY_RECEIPT_ID = "select * from receipt_product where receipt_id = ?";
    public static final String SELECT_ALL_FROM_RECEIPT_BY_USER_ID_AND_ACTIVE = "select * from receipt where user_id = ? and active = ?";
    public static final String SELECT_PAGE_RECEIPTS_BY_ID = "select * from receipt where id = ? LIMIT ?, ?";
    public static final String COUNT_ALL_RECEIPTS = "SELECT COUNT(*) FROM receipt";
    public static final String COUNT_ALL_RECEIPTS_BY_ID = "SELECT COUNT(*) FROM receipt WHERE id = ?";
    public static final String UPDATE_RECEIPT_ACTIVE = "UPDATE receipt SET active = ?, total = ? where id = ?";
    public static final String FIELD_RECEIPT_ID = "id";
    public static final String FIELD_RECEIPT_DATE_OF_CREATION = "date_of_creation";
    public static final String FIELD_RECEIPT_USER_ID = "user_id";
    public static final String FIELD_RECEIPT_TOTAL = "total";
    public static final String FIELD_RECEIPT_ACTIVE = "active";
    public static final String INSERT_PRODUCT_INTO_RECEIPT_PRODUCT = "insert into receipt_product (receipt_id,product_id,capacity,price) values (?,?,?,?)";
    public static final String DELETE_RECEIPT = "delete from receipt where receipt.id = ?";
    public static final String UPDATE_PRODUCT_IN_RECEIPT = "UPDATE receipt_product SET capacity = ?, price = ? where receipt_id = ? AND product_id = ?";
    public static final String DELETE_PRODUCT_IN_RECEIPT = "delete from receipt_product where receipt_id = ? AND product_id = ?";



    public static final String PAGE_SIZE = "7";

//    JOIN

    public static final String JOIN_USER_LOGIN_TO_RECEIPT = "select receipt.id, receipt.date_of_creation, receipt.user_id, user.login, receipt.active from receipt " +
            "join user on user.id = receipt.user_id LIMIT ?, ?";

}
