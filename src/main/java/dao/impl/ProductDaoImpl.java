package dao.impl;

import dao.ProductDao;
import entity.Product;
import org.apache.log4j.Logger;
import util.MySQLConnector;
import util.SQLConstants;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDaoImpl implements ProductDao {

    private static final Logger LOG = Logger.getLogger(ProductDaoImpl.class);

    /**
     * Creating product in database.
     *
     * @param product product, that should be created
     * @return created product from database
     */
    @Override
    public Product createProduct(Product product) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try (Connection connection = MySQLConnector.getConnection()){
            preparedStatement = connection.prepareStatement(SQLConstants.INSERT_PRODUCT, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, product.getName());
            preparedStatement.setString(2, product.getCode());
            preparedStatement.setString(3, product.getCapacityType());
            preparedStatement.setDouble(4,product.getCapacity());
            preparedStatement.setDouble(5,product.getPrice());
            preparedStatement.executeUpdate();
            LOG.info("Connection installed successfully" );
            resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet != null && resultSet.next()){
                product.setId(resultSet.getInt(1));
                LOG.info("Product created successfully");
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
        return product;
    }

    /**
     * Receiving product by name of product.
     *
     * @param name Name of product for searching
     * @return entity of product from database
     */
    @Override
    public Product getProductByName(String name) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try (Connection connection = MySQLConnector.getConnection()){
            preparedStatement = connection.prepareStatement(SQLConstants.SELECT_PRODUCT_BY_NAME);
            preparedStatement.setString(1,name);
            resultSet = preparedStatement.executeQuery();
            LOG.info("Connection installed successfully" );
            if (resultSet.next()) {
                LOG.info("Product received successfully");
                return mapProduct(resultSet);
            }
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
     * Receiving product by code of product.
     *
     * @param code Code of product for searching
     * @return entity of product from database
     */
    @Override
    public Product getProductByCode(String code) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try (Connection connection = MySQLConnector.getConnection()){
            preparedStatement = connection.prepareStatement(SQLConstants.SELECT_PRODUCT_BY_CODE);
            preparedStatement.setString(1,code);
            resultSet = preparedStatement.executeQuery();
            LOG.info("Connection installed successfully" );
            if (resultSet.next()) {
                LOG.info("Product received successfully");
                return mapProduct(resultSet);
            }
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
     * Receiving all products from database.
     *
     * @return list of products from database
     */
    @Override
    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        try (Connection connection = MySQLConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQLConstants.SELECT_ALL_FROM_PRODUCT);
             ResultSet resultSet = preparedStatement.executeQuery()
        ) {
            LOG.info("Connection installed successfully" );
            while (resultSet.next()){
                products.add(mapProduct(resultSet));
            }
            LOG.info("Products received successfully" );
        } catch (Exception e) {
            LOG.error(e.getMessage() );
        }
        return products;
    }

    /**
     * Receiving products from receipt.
     *
     * @param receiptId Id of receipt for searching products
     * @return list of products from receipt
     */
    @Override
    public List<Product> getProductsByReceiptId(int receiptId) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Product> products = new ArrayList<>();
        try (Connection connection = MySQLConnector.getConnection()){
            preparedStatement = connection.prepareStatement(SQLConstants.SELECT_PRODUCTS_BY_RECEIPT_ID);
            preparedStatement.setInt(1,receiptId);
            resultSet = preparedStatement.executeQuery();
            LOG.info("Connection installed successfully" );
            while (resultSet.next()){
                products.add(mapProductFromReceipt(resultSet));
            }
            LOG.info("Products by receiptId received successfully" );
            return products;
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
     * Receiving count of products.
     *
     * @return number of products
     */
    @Override
    public int getProductsCount() {
        int numberOfProducts = 0;
        try (Connection connection = MySQLConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQLConstants.COUNT_ALL_PRODUCTS);
             ResultSet resultSet = preparedStatement.executeQuery()
        ) {
            LOG.info("Connection installed successfully" );
            resultSet.next();
            numberOfProducts = resultSet.getInt(1);
            LOG.info("Product count received successfully" );
        } catch (Exception e) {
            LOG.error(e.getMessage());
        }
        return numberOfProducts;
    }

    /**
     * Receiving count of products by product_name.
     *
     * @param name Name of product for searching
     * @return number of products with parameter Name
     */
    @Override
    public int getProductsCountByName(String name) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        int numberOfProducts = 0;
        try (Connection connection = MySQLConnector.getConnection()) {
            preparedStatement = connection.prepareStatement(SQLConstants.COUNT_ALL_PRODUCTS_BY_NAME);
            preparedStatement.setString(1,name);
            resultSet = preparedStatement.executeQuery();
            LOG.info("Connection installed successfully" );
            resultSet.next();
            numberOfProducts = resultSet.getInt(1);
            LOG.info("Product count by Name received successfully" );
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
        return numberOfProducts;
    }

    /**
     * Receiving count of products by product_code.
     *
     * @param code Code of product for searching
     * @return number of products with parameter Code
     */
    @Override
    public int getProductsCountByCode(String code) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        int numberOfProducts = 0;
        try (Connection connection = MySQLConnector.getConnection()) {
            preparedStatement = connection.prepareStatement(SQLConstants.COUNT_ALL_PRODUCTS_BY_CODE);
            preparedStatement.setString(1,code);
            resultSet = preparedStatement.executeQuery();
            LOG.info("Connection installed successfully" );
            resultSet.next();
            numberOfProducts = resultSet.getInt(1);
            LOG.info("Products Count By Code received successfully" );
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
        return numberOfProducts;
    }

    /**
     * Receiving all products with paging.
     *
     * @param page Number of page on which products will be show
     * @param size Number of products, that should be showed on page
     * @return list of products with paging
     */
    @Override
    public List<Product> getPagingProducts(int page, int size) {
        List<Product> products = new ArrayList<>();
        PreparedStatement preparedStatement = null;
        try (Connection connection = MySQLConnector.getConnection()) {
            preparedStatement = connection.prepareStatement(SQLConstants.SELECT_PAGE_FROM_PRODUCT);
            preparedStatement.setInt(1,page*size);
            preparedStatement.setInt(2,size);
            ResultSet resultSet = preparedStatement.executeQuery();
            LOG.info("Connection installed successfully" );
            while (resultSet.next()){
                products.add(mapProduct(resultSet));
            }
            LOG.info("Paging products received successfully" );
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
        return products;
    }


    /**
     * Receiving searchable products with paging by code.
     *
     * @param page Number of page on which products will be show after search
     * @param size Number of products, that should be showed on page after search
     * @param code Code of product for searching
     * @return list of products with paging after search
     */
    @Override
    public List<Product> getPagingSearchProductsByCode(int page, int size, String code) {
        List<Product> products = new ArrayList<>();
        PreparedStatement preparedStatement = null;
        try (Connection connection = MySQLConnector.getConnection()) {
            preparedStatement = connection.prepareStatement(SQLConstants.SELECT_PAGE_PRODUCTS_BY_CODE);
            preparedStatement.setString(1,code);
            preparedStatement.setInt(2,page*size);
            preparedStatement.setInt(3,size);
            ResultSet resultSet = preparedStatement.executeQuery();
            LOG.info("Connection installed successfully" );
            while (resultSet.next()){
                products.add(mapProduct(resultSet));
            }
            LOG.info("Paging Search Products By Code received successfully" );
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
        return products;
    }


    /**
     * Receiving searchable products with paging by name.
     *
     * @param page Number of page on which products will be show after search
     * @param size Number of products, that should be showed on page after search
     * @param name Name of product for searching
     * @return list of products with paging after search
     */
    @Override
    public List<Product> getPagingSearchProductsByName(int page, int size, String name) {
        List<Product> products = new ArrayList<>();
        PreparedStatement preparedStatement = null;
        try (Connection connection = MySQLConnector.getConnection()) {
            preparedStatement = connection.prepareStatement(SQLConstants.SELECT_PAGE_PRODUCTS_BY_NAME);
            preparedStatement.setString(1,name);
            preparedStatement.setInt(2,page*size);
            preparedStatement.setInt(3,size);
            ResultSet resultSet = preparedStatement.executeQuery();
            LOG.info("Connection installed successfully" );

            while (resultSet.next()){
                products.add(mapProduct(resultSet));
            }
            LOG.info("Paging Search Products By Name received successfully" );
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
        return products;
    }


    /**
     * Updating product in database.
     *
     * @param product Updated product
     * @return entity of product, that was updated
     */
    @Override
    public Product updateProduct(Product product) {
        PreparedStatement preparedStatement = null;
        try (Connection connection = MySQLConnector.getConnection()){
            preparedStatement = connection.prepareStatement(SQLConstants.UPDATE_PRODUCT);
            preparedStatement.setString(1, product.getName());
            preparedStatement.setString(2, product.getCode());
            preparedStatement.setString(3,product.getCapacityType());
            preparedStatement.setDouble(4,product.getCapacity());
            preparedStatement.setDouble(5,product.getPrice());
            preparedStatement.setInt(6,product.getId());
            preparedStatement.executeUpdate();
            LOG.info("Connection installed successfully" );
            LOG.info("Product updated successfully" );
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
        }
        return product;
    }

    /**
     * Deleting product from database by id.
     *
     * @param productId Id of product, that should be deleted
     * @return true if product was deleted
     */
    @Override
    public boolean deleteProduct(int productId) {
        PreparedStatement preparedStatement = null;
        try (Connection connection = MySQLConnector.getConnection()){
            preparedStatement = connection.prepareStatement(SQLConstants.DELETE_PRODUCT);
            preparedStatement.setInt(1,productId);
            preparedStatement.executeUpdate();
            LOG.info("Connection installed successfully" );
            LOG.info("Product deleted successfully" );
            return true;
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
        }
        return false;
    }

    /**
     * Receiving product by id.
     *
     * @param productId Id of product, that should be received
     * @return entity of product
     */
    @Override
    public Product getProductById(int productId) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try (Connection connection = MySQLConnector.getConnection()){
            preparedStatement = connection.prepareStatement(SQLConstants.SELECT_PRODUCT_BY_ID);
            preparedStatement.setInt(1,productId);
            resultSet = preparedStatement.executeQuery();
            LOG.info("Connection installed successfully" );
            if (resultSet.next()) {
                LOG.info("Product By Id received successfully");
                return mapProduct(resultSet);
            }
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
     * Mapping product from ResultSet.
     *
     * @param result ResultSet that received from executeQuery
     * @return entity Product
     * @throws SQLException if a database access failed
     */
    private Product mapProduct(ResultSet result) throws SQLException {
        Product product = new Product();
        product.setId(result.getInt(SQLConstants.FIELD_PRODUCT_ID));
        product.setName(result.getString(SQLConstants.FIELD_PRODUCT_NAME));
        product.setCapacityType(result.getString(SQLConstants.FIELD_PRODUCT_CAPACITY_TYPE));
        product.setCapacity(result.getDouble(SQLConstants.FIELD_PRODUCT_CAPACITY));
        product.setPrice(result.getDouble(SQLConstants.FIELD_PRODUCT_PRICE));
        product.setCode(result.getString(SQLConstants.FIELD_PRODUCT_CODE));
        return product;
    }

    /**
     * Mapping product from receipt from ResultSet.
     *
     * @param result ResultSet that received from executeQuery
     * @return entity of Product
     * @throws SQLException if a database access failed
     */
    private Product mapProductFromReceipt(ResultSet result) throws SQLException {
        Product p = getProductById(result.getInt(SQLConstants.FIELD_RECEIPT_PRODUCT_ID));
        p.setCapacity(result.getDouble(SQLConstants.FIELD_RECEIPT_PRODUCT_CAPACITY));
        p.setPrice(result.getDouble(SQLConstants.FIELD_RECEIPT_PRODUCT_PRICE));
        return p;
    }
}
