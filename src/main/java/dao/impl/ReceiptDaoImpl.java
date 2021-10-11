package dao.impl;

import dao.ReceiptDao;
import entity.Product;
import entity.Receipt;
import org.apache.log4j.Logger;
import util.MySQLConnector;
import util.SQLConstants;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ReceiptDaoImpl implements ReceiptDao {

    private static final Logger LOG = Logger.getLogger(ReceiptDaoImpl.class);

    /**
     * Creating new receipt in database.
     *
     * @param userId Id of user, who creating receipt
     * @return created entity from database
     */
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
            LOG.info("Connection installed successfully" );
            resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet != null && resultSet.next()){
                LOG.info("Receipt created successfully" );
                receipt.setId(resultSet.getInt(1));
            }
        } catch (Exception e) {
            LOG.error(e.getMessage());
        }finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            } catch (SQLException throwables) {
                LOG.error(throwables.getMessage());
            }

            try {
                if (resultSet != null) {
                    resultSet.close();
                }
            } catch (SQLException throwables) {
                LOG.error(throwables.getMessage());
            }
        }
        return receipt;
    }

    /**
     * Closing receipt in database and counting total amount of receipt.
     *
     * @param receiptId Id of receipt, that should be closed
     * @return true if receipt was closed successfully
     */
    public boolean close(int receiptId, double total){
        PreparedStatement preparedStatement = null;
        try (Connection connection = MySQLConnector.getConnection()){
            preparedStatement = connection.prepareStatement(SQLConstants.UPDATE_RECEIPT_ACTIVE);
            preparedStatement.setInt(1,0);
            preparedStatement.setDouble(2, total);
            preparedStatement.setInt(3,receiptId);
            preparedStatement.executeUpdate();
            LOG.info("Connection installed successfully" );
            LOG.info("Receipt closed successfully" );
            return true;
        } catch (Exception e) {
            LOG.error(e.getMessage());
        }finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException throwables) {
                    LOG.error(throwables.getMessage());
                }
            }
        }
        return false;
    }

    /**
     * Adding product to receipt in database.
     *
     * @param product Entity of product, that should be added
     * @param receiptId Id of receipt in which product should be added
     * @return true if product was added
     */
    public boolean addProductToReceipt(Product product, int receiptId){
        PreparedStatement preparedStatement = null;
        try (Connection connection = MySQLConnector.getConnection()){
            preparedStatement = connection.prepareStatement(SQLConstants.INSERT_PRODUCT_INTO_RECEIPT_PRODUCT);
            preparedStatement.setInt(1,receiptId);
            preparedStatement.setInt(2,product.getId());
            preparedStatement.setDouble(3,product.getCapacity());
            preparedStatement.setDouble(4,(product.getPrice()*product.getCapacity()));
            preparedStatement.executeUpdate();
            LOG.info("Connection installed successfully");
            LOG.info("Product added to Receipt successfully" );
            return true;
        } catch (Exception e) {
            LOG.error(e.getMessage());
        }finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException throwables) {
                    LOG.error(throwables.getMessage());
                }
            }
        }
        return false;
    }


    /**
     * Updating product in receipt.
     *
     * @param product Entity of updated product
     * @param receiptId Id of receipt in which product should be updated
     * @return true if product was updated
     */
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
            LOG.info("Connection installed successfully");
            LOG.info("Product in Receipt updated successfully");
            return true;
        } catch (Exception e) {
            LOG.error(e.getMessage());
        }finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException throwables) {
                    LOG.error(throwables.getMessage());
                }
            }
        }
        return false;
    }

    /**
     * Deleting product from receipt.
     *
     * @param productId Id of product, that should be deleted
     * @param receiptId Id of receipt in which product should be deleted
     * @return true if product was deleted from receipt
     */
    @Override
    public boolean deleteProduct(int productId, int receiptId) {
        PreparedStatement preparedStatement = null;
        try (Connection connection = MySQLConnector.getConnection()){
            preparedStatement = connection.prepareStatement(SQLConstants.DELETE_PRODUCT_IN_RECEIPT);
            preparedStatement.setInt(1,receiptId);
            preparedStatement.setInt(2,productId);
            preparedStatement.executeUpdate();
            LOG.info("Connection installed successfully");
            LOG.info("Product deleted from Receipt successfully");
            return true;
        } catch (Exception e) {
            LOG.error(e.getMessage());
        }finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException throwables) {
                    LOG.error(throwables.getMessage());
                }
            }
        }
        return false;
    }

    /**
     * Deleting receipt in database.
     *
     * @param id Id of receipt, that should be deleted
     * @return true if receipt was deleted
     */
    @Override
    public boolean deleteById(int id) {
        PreparedStatement preparedStatement = null;
        try (Connection connection = MySQLConnector.getConnection()){
            preparedStatement = connection.prepareStatement(SQLConstants.DELETE_RECEIPT);
            preparedStatement.setInt(1,id);
            preparedStatement.executeUpdate();
            LOG.info("Connection installed successfully");
            LOG.info("Receipt deleted successfully");
            return true;
        } catch (Exception e) {
            LOG.error(e.getMessage());
        }finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException throwables) {
                    LOG.error(throwables.getMessage());
                }
            }
        }
        return false;
    }

    /**
     * Receiving receipt from database by id of receipt.
     *
     * @param receiptId Id of receipt for searching receipt in database
     * @return entity of receipt from database where id of receipt is equal to param
     */
    @Override
    public Receipt getById(int receiptId) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try (Connection connection = MySQLConnector.getConnection()){
            preparedStatement = connection.prepareStatement(SQLConstants.SELECT_RECEIPT_BY_ID);
            preparedStatement.setInt(1,receiptId);
            resultSet = preparedStatement.executeQuery();
            LOG.info("Connection installed successfully");
            resultSet.next();
            LOG.info("Receipt received by id successfully");
            return mapReceipt(resultSet);
        } catch (Exception e) {
            LOG.error(e.getMessage());
        }finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException throwables) {
                    LOG.error(throwables.getMessage());
                }
            }
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException throwables) {
                    LOG.error(throwables.getMessage());
                }
            }
        }
        return null;
    }

    /**
     * Receiving receipt by id and owner of this receipt.
     *
     * @param receiptId Id of receipt for searching receipt in database
     * @return entity of receipt, that was mapped with owner_name
     */
    @Override
    public Receipt getByIdWithOwner(int receiptId) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try (Connection connection = MySQLConnector.getConnection()){
            preparedStatement = connection.prepareStatement(SQLConstants.SELECT_RECEIPT_BY_ID_WITH_OWNER_NAME);
            preparedStatement.setInt(1,receiptId);
            resultSet = preparedStatement.executeQuery();
            LOG.info("Connection installed successfully");
            resultSet.next();
            LOG.info(" Receipt By Id With Owner received successfully");
            return mapReceiptWithOwner(resultSet);
        } catch (Exception e) {
            LOG.error(e.getMessage());
        }finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException throwables) {
                    LOG.error(throwables.getMessage());
                }
            }
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException throwables) {
                    LOG.error(throwables.getMessage());
                }
            }
        }
        return null;
    }


    /**
     * Receiving all receipts with paging.
     *
     * @param page Number of page on which receipts will be show
     * @param size Number of receipts, that should be showed on page
     * @return list of receipts with paging
     */
    @Override
    public List<Receipt> getPagingReceipts(int page, int size) {
        List<Receipt> receipts = new ArrayList<>();
        PreparedStatement preparedStatement = null;
        try (Connection connection = MySQLConnector.getConnection()) {
            preparedStatement = connection.prepareStatement(SQLConstants.JOIN_USER_LOGIN_TO_RECEIPT);
            preparedStatement.setInt(1,page*size);
            preparedStatement.setInt(2,size);
            ResultSet resultSet = preparedStatement.executeQuery();
            LOG.info("Connection installed successfully");
            while (resultSet.next()){
                receipts.add(mapReceiptWithOwnerName(resultSet));
            }
            LOG.info("Paging Receipts received successfully");
        } catch (Exception e) {
            LOG.error(e.getMessage());
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            } catch (SQLException throwables) {
                LOG.error(throwables.getMessage());
            }
        }
        return receipts;
    }

    /**
     * Receiving count of receipts.
     *
     * @return number of receipts
     */
    @Override
    public int getReceiptsCount() {
        int numberOfProducts = 0;
        try (Connection connection = MySQLConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQLConstants.COUNT_ALL_RECEIPTS);
             ResultSet resultSet = preparedStatement.executeQuery()
        ) {
            LOG.info("Connection installed successfully");
            resultSet.next();
            numberOfProducts = resultSet.getInt(1);
            LOG.info("Receipts count received successfully");
        } catch (Exception e) {
            LOG.error(e.getMessage());
        }
        return numberOfProducts;
    }

    /**
     * Receiving all receipts by owner_id and status.
     *
     * @param userId Id of user, who created receipt
     * @param active Status of receipt
     * @return list of receipts, that were created by user
     */
    @Override
    public List<Receipt> getAllReceiptsByUserIdAndActive(int userId, boolean active) {
        List<Receipt> receipts = new ArrayList<>();
        PreparedStatement preparedStatement = null;
        try (Connection connection = MySQLConnector.getConnection()) {
            preparedStatement = connection.prepareStatement(SQLConstants.SELECT_ALL_FROM_RECEIPT_BY_USER_ID_AND_ACTIVE);
            preparedStatement.setInt(1,userId);
            preparedStatement.setBoolean(2,active);
            ResultSet resultSet = preparedStatement.executeQuery();
            LOG.info("Connection installed successfully");
            while (resultSet.next()){
                receipts.add(mapReceipt(resultSet));
            }
            LOG.info("All Receipts By User Id And Active received successfully");
        } catch (Exception e) {
            LOG.error(e.getMessage());
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            } catch (SQLException throwables) {
                LOG.error(throwables.getMessage());
            }
        }
        return receipts;
    }

    public List<Receipt> getAllClosedReceiptsBetweenDate(LocalDateTime localDateTime1, LocalDateTime localDateTime2) {
        List<Receipt> receipts = new ArrayList<>();
        PreparedStatement preparedStatement = null;
        try (Connection connection = MySQLConnector.getConnection()) {
            preparedStatement = connection.prepareStatement(SQLConstants.SELECT_ALL_FROM_RECEIPT_BY_ACTIVE_AND_CREATION_DATE);
            preparedStatement.setInt(1, 0);
            preparedStatement.setTimestamp(2, Timestamp.valueOf(localDateTime1));
            preparedStatement.setTimestamp(3, Timestamp.valueOf(localDateTime2));
            ResultSet resultSet = preparedStatement.executeQuery();
            LOG.info("Connection installed successfully");
            while (resultSet.next()) {
                receipts.add(mapReceipt(resultSet));
            }
            LOG.info("All Closed Receipts Between Dates received successfully");
        } catch (Exception e) {
            LOG.error(e.getMessage());
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            } catch (SQLException throwables) {
                LOG.error(throwables.getMessage());
            }
        }
            return receipts;
        }

    /**
     * Mapping of receipt from ResultSet.
     *
     * @param result ResultSet that received from executeQuery
     * @return entity Receipt
     * @throws SQLException if a database access failed
     */
    private Receipt mapReceipt(ResultSet result) throws SQLException {
        Receipt receipt = new Receipt();
        receipt.setId(result.getInt(SQLConstants.FIELD_RECEIPT_ID));
        receipt.setCreationDateTime( result.getTimestamp(SQLConstants.FIELD_RECEIPT_DATE_OF_CREATION).toLocalDateTime());
        receipt.setOwnerId(result.getInt(SQLConstants.FIELD_RECEIPT_USER_ID));
        receipt.setTotal(result.getDouble(SQLConstants.FIELD_RECEIPT_TOTAL));
        receipt.setActive(result.getBoolean(SQLConstants.FIELD_RECEIPT_ACTIVE));
        return receipt;
    }

    /**
     * Mapping of receipt with owner from ResultSet.
     *
     * @param result ResultSet that received from executeQuery
     * @return entity Receipt with owner
     * @throws SQLException if a database access failed
     */
    private Receipt mapReceiptWithOwner(ResultSet result) throws SQLException {
        Receipt receipt = new Receipt();
        receipt.setId(result.getInt(SQLConstants.FIELD_RECEIPT_ID));
        receipt.setCreationDateTime( result.getTimestamp(SQLConstants.FIELD_RECEIPT_DATE_OF_CREATION).toLocalDateTime());
        receipt.setOwnerName(result.getString(SQLConstants.FIELD_USER_LOGIN));
        return receipt;
    }

    /**
     * Mapping of receipt with name of owner from ResultSet.
     *
     * @param result ResultSet that received from executeQuery
     * @return entity Receipt with name of owner
     * @throws SQLException if a database access failed
     */
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
