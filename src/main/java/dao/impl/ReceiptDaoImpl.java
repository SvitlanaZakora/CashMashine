package dao.impl;

import dao.ReceiptDao;
import entity.Product;
import entity.Receipt;
import util.MySQLConnector;
import util.SQLConstants;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
            preparedStatement.setInt(1,0);
            preparedStatement.setInt(2,receiptId);
            preparedStatement.executeUpdate();
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
            preparedStatement.executeUpdate();
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
            preparedStatement.setDouble(2,product.getCapacity()*product.getPrice());
            preparedStatement.setInt(3,receiptId);
            preparedStatement.setInt(4,product.getId());
            preparedStatement.executeUpdate();
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
            preparedStatement.executeUpdate();
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
            preparedStatement.executeUpdate();
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
            resultSet.next();
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

    @Override
    public Receipt getByIdWithOwner(int receiptId) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try (Connection connection = MySQLConnector.getConnection()){
            preparedStatement = connection.prepareStatement(SQLConstants.SELECT_RECEIPT_BY_ID_WITH_OWNER_NAME);
            preparedStatement.setInt(1,receiptId);
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return mapReceiptWithOwner(resultSet);
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


    @Override
    public List<Receipt> getPagingReceipts(int page, int size) {
        List<Receipt> receipts = new ArrayList<>();
        PreparedStatement preparedStatement = null;
        try (Connection connection = MySQLConnector.getConnection()) {
            preparedStatement = connection.prepareStatement(SQLConstants.JOIN_USER_LOGIN_TO_RECEIPT);
            preparedStatement.setInt(1,page*size);
            preparedStatement.setInt(2,size);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                receipts.add(mapReceiptWithOwnerName(resultSet));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            } catch (SQLException throwables) {
                System.out.println(throwables.getMessage());
            }
        }
        return receipts;
    }

    @Override
    public int getReceiptsCount() {
        int numberOfProducts = 0;
        try (Connection connection = MySQLConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQLConstants.COUNT_ALL_RECEIPTS);
             ResultSet resultSet = preparedStatement.executeQuery()
        ) {
            resultSet.next();
            numberOfProducts = resultSet.getInt(1);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return numberOfProducts;
    }

    @Override
    public List<Receipt> getAllReceiptsByUserIdAndActive(int userId, boolean active) {
        List<Receipt> receipts = new ArrayList<>();
        PreparedStatement preparedStatement = null;
        try (Connection connection = MySQLConnector.getConnection()) {
            preparedStatement = connection.prepareStatement(SQLConstants.SELECT_ALL_FROM_RECEIPT_BY_USER_ID_AND_ACTIVE);
            preparedStatement.setInt(1,userId);
            preparedStatement.setBoolean(2,active);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                receipts.add(mapReceipt(resultSet));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            } catch (SQLException throwables) {
                System.out.println(throwables.getMessage());
            }
        }
        return receipts;
    }

    private Receipt mapReceipt(ResultSet result) throws SQLException {
        Receipt receipt = new Receipt();
        receipt.setId(result.getInt(SQLConstants.FIELD_RECEIPT_ID));
        receipt.setCreationDateTime( result.getTimestamp(SQLConstants.FIELD_RECEIPT_DATE_OF_CREATION).toLocalDateTime());
        receipt.setOwnerId(result.getInt(SQLConstants.FIELD_RECEIPT_USER_ID));
        receipt.setActive(result.getBoolean(SQLConstants.FIELD_RECEIPT_ACTIVE));
        return receipt;
    }

    private Receipt mapReceiptWithOwner(ResultSet result) throws SQLException {
        Receipt receipt = new Receipt();
        receipt.setId(result.getInt(SQLConstants.FIELD_RECEIPT_ID));
        receipt.setCreationDateTime( result.getTimestamp(SQLConstants.FIELD_RECEIPT_DATE_OF_CREATION).toLocalDateTime());
        receipt.setOwnerName(result.getString(SQLConstants.FIELD_USER_LOGIN));
        return receipt;
    }

    private Receipt mapReceiptWithOwnerName(ResultSet result) throws SQLException {
        Receipt receipt = new Receipt();
        receipt.setId(result.getInt(SQLConstants.FIELD_RECEIPT_ID));
        receipt.setCreationDateTime( result.getTimestamp(SQLConstants.FIELD_RECEIPT_DATE_OF_CREATION).toLocalDateTime());
        receipt.setOwnerId(result.getInt(SQLConstants.FIELD_RECEIPT_USER_ID));
        receipt.setOwnerName(result.getString(SQLConstants.FIELD_USER_LOGIN));
        receipt.setActive(result.getBoolean(SQLConstants.FIELD_RECEIPT_ACTIVE));
        return receipt;
    }
}
