package dao.impl;

import dao.ReceiptDao;
import entity.Product;
import entity.Receipt;
import util.MySQLConnector;
import util.SQLConstants;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class ReceiptDaoImpl implements ReceiptDao {

    public Receipt create(int userId){
        Receipt receipt = new Receipt();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try (Connection connection = MySQLConnector.getConnection()){
            preparedStatement = connection.prepareStatement(SQLConstants.INSERT_RECEIPT, Statement.RETURN_GENERATED_KEYS);
            receipt.setCreationDateTime(LocalDateTime.now());
            preparedStatement.setTimestamp(1,Timestamp.valueOf(receipt.getCreationDateTime()));
            preparedStatement.setInt(2,userId);
            receipt.setProducts(new ArrayList<>());
            receipt.setActive(true);
            preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet != null && resultSet.next()){
                receipt.setId(resultSet.getInt(1));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            } catch (SQLException throwables) {
                System.out.println(throwables.getMessage());
            }

            try {
                if (resultSet != null) {
                    resultSet.close();
                }
            } catch (SQLException throwables) {
                System.out.println(throwables.getMessage());
            }
        }
        return receipt;
    }

    public boolean close(int receiptId){
        PreparedStatement preparedStatement = null;
        try (Connection connection = MySQLConnector.getConnection()){
            preparedStatement = connection.prepareStatement(SQLConstants.UPDATE_RECEIPT_ACTIVE);
            preparedStatement.setBoolean(1,false);
            preparedStatement.setInt(2,receiptId);
            preparedStatement.executeQuery();
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException throwables) {
                    System.out.println(throwables.getMessage());
                }
            }
        }
        return false;
    }

    public boolean addProductToReceipt(Product product, int receiptId){
        PreparedStatement preparedStatement = null;
        try (Connection connection = MySQLConnector.getConnection()){
            preparedStatement = connection.prepareStatement(SQLConstants.INSERT_PRODUCT_INTO_RECEIPT_PRODUCT);
            preparedStatement.setInt(1,receiptId);
            preparedStatement.setInt(2,product.getId());
            preparedStatement.setDouble(3,product.getCapacity());
            preparedStatement.setDouble(4,(product.getPrice()*product.getCapacity()));
            preparedStatement.executeQuery();
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException throwables) {
                    System.out.println(throwables.getMessage());
                }
            }
        }
        return false;
    }

    @Override
    public boolean updateProduct(Product product, int receiptId) {
        PreparedStatement preparedStatement = null;
        try (Connection connection = MySQLConnector.getConnection()){
            preparedStatement = connection.prepareStatement(SQLConstants.UPDATE_PRODUCT_IN_RECEIPT);
            preparedStatement.setDouble(1,product.getCapacity());
            preparedStatement.setInt(2,receiptId);
            preparedStatement.setInt(3,product.getId());
            preparedStatement.executeQuery();
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException throwables) {
                    System.out.println(throwables.getMessage());
                }
            }
        }
        return false;
    }

    @Override
    public boolean deleteProduct(int productId, int receiptId) {
        PreparedStatement preparedStatement = null;
        try (Connection connection = MySQLConnector.getConnection()){
            preparedStatement = connection.prepareStatement(SQLConstants.DELETE_PRODUCT_IN_RECEIPT);
            preparedStatement.setInt(1,receiptId);
            preparedStatement.setInt(2,productId);
            preparedStatement.executeQuery();
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException throwables) {
                    System.out.println(throwables.getMessage());
                }
            }
        }
        return false;
    }

    @Override
    public boolean deleteById(int id) {
        PreparedStatement preparedStatement = null;
        try (Connection connection = MySQLConnector.getConnection()){
            preparedStatement = connection.prepareStatement(SQLConstants.DELETE_RECEIPT);
            preparedStatement.setInt(1,id);
            preparedStatement.executeQuery();
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException throwables) {
                    System.out.println(throwables.getMessage());
                }
            }
        }
        return false;
    }

    @Override
    public Receipt getById(int receiptId) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try (Connection connection = MySQLConnector.getConnection()){
            preparedStatement = connection.prepareStatement(SQLConstants.SELECT_RECEIPT_BY_ID);
            preparedStatement.setInt(1,receiptId);
            resultSet = preparedStatement.executeQuery();
            return mapReceipt(resultSet);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException throwables) {
                    System.out.println(throwables.getMessage());
                }
            }
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException throwables) {
                    System.out.println(throwables.getMessage());
                }
            }
        }
        return null;
    }

    private Receipt mapReceipt(ResultSet result) throws SQLException {
        Receipt receipt = new Receipt();
        receipt.setId(result.getInt(SQLConstants.FIELD_RECEIPT_ID));
        receipt.setCreationDateTime( result.getTimestamp(SQLConstants.FIELD_RECEIPT_DATE_OF_CREATION).toLocalDateTime());
        receipt.setOwnerId(result.getInt(SQLConstants.FIELD_RECEIPT_USER_ID));
        receipt.setActive(result.getBoolean(SQLConstants.FIELD_RECEIPT_ACTIVE));
        return receipt;
    }
}
