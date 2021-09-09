package util;

public class SQLConstants {

    public static final String INSERT_USER = "insert into user (login,pass,role) values (?,?,?)";
    public static final String SELECT_USER_BY_LOGIN = "select * from user where login = ?";
    public static final String SELECT_ALL_FROM_USER = "select * from user";
    public static final String FIELD_USER_ID = "id";
    public static final String FIELD_USER_LOGIN = "login";
    public static final String FIELD_USER_ROLE = "role";
    public static final String FIELD_USER_PASS = "pass";
    public static final String UPDATE_USER = "UPDATE user SET login = ?, pass = ?, role = ? where id = ?";
    public static final String DELETE_USER = "delete from user where user.id = ?";
    public static final String SELECT_USER_BY_ID = "select * from user where id = ?";

    public static final String INSERT_PRODUCT = "insert into product (name,code,capacityType,capacity,price) values (?,?,?,?,?)";
    public static final String SELECT_PRODUCT_BY_NAME = "select * from product where name = ?";
    public static final String SELECT_PRODUCT_BY_CODE = "select * from product where code = ?";
    public static final String FIELD_PRODUCT_ID = "id";
    public static final String FIELD_PRODUCT_NAME = "name";
    public static final String FIELD_PRODUCT_CAPACITY_TYPE = "capacityType";
    public static final String FIELD_PRODUCT_CAPACITY = "capacity";
    public static final String FIELD_PRODUCT_PRICE = "price";
    public static final String FIELD_PRODUCT_CODE = "code";
    public static final String SELECT_ALL_FROM_PRODUCT = "select * from product";
    public static final String UPDATE_PRODUCT = "UPDATE product SET name = ?, code = ?, capacityType = ?, capacity = ?, price = ? where id = ?";
    public static final String DELETE_PRODUCT = "delete from product where product.id = ?";
    public static final String SELECT_PRODUCT_BY_ID = "select * from product where id = ?";

    public static final String INSERT_RECEIPT = "insert into receipt (date_of_creation,user_id) values (?,?)";
    public static final String SELECT_RECEIPT_BY_ID = "select * from receipt where id = ?";
    public static final String UPDATE_RECEIPT_ACTIVE = "UPDATE receipt SET active = ? where id = ?";
    public static final String FIELD_RECEIPT_ID = "id";
    public static final String FIELD_RECEIPT_DATE_OF_CREATION = "date_of_creation";
    public static final String FIELD_RECEIPT_USER_ID = "user_id";
    public static final String FIELD_RECEIPT_ACTIVE = "active";
    public static final String INSERT_PRODUCT_INTO_RECEIPT_PRODUCT = "insert into receipt_product (receipt_id,product_id,capacity,price) values (?,?,?,?)";
    public static final String DELETE_RECEIPT = "delete from receipt where receipt.id = ?";
    public static final String UPDATE_PRODUCT_IN_RECEIPT = "UPDATE receipt_product SET capacity = ? where receipt_id = ? AND product_id = ?";
    public static final String DELETE_PRODUCT_IN_RECEIPT = "delete from receipt_product where receipt_id = ? AND product_id = ?";
}
