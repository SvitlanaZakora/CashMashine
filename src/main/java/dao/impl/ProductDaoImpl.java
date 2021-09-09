package dao.impl;

import dao.ProductDao;
import entity.Product;
import entity.Role;
import entity.User;
import util.MySQLConnector;
import util.SQLConstants;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDaoImpl implements ProductDao {
    @Override
    public Product createProduct(Product product) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try (Connection connection = MySQLConnector.getConnection()){
            preparedStatement = connection.prepareStatement(SQLConstants.INSERT_PRODUCT, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, product.getName());
            preparedStatement.setString(2, product.getCapacityType());
            preparedStatement.setDouble(3,product.getCapacity());
            preparedStatement.setDouble(4,product.getPrice());
            preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet != null && resultSet.next()){
                product.setId(resultSet.getInt(1));
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
        return product;
    }

    @Override
    public Product getProductByName(String name) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try (Connection connection = MySQLConnector.getConnection()){
            preparedStatement = connection.prepareStatement(SQLConstants.SELECT_PRODUCT_BY_NAME);
            preparedStatement.setString(1,name);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) return mapProduct(resultSet);
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
    public Product getProductByCode(String code) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try (Connection connection = MySQLConnector.getConnection()){
            preparedStatement = connection.prepareStatement(SQLConstants.SELECT_PRODUCT_BY_CODE);
            preparedStatement.setString(1,code);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) return mapProduct(resultSet);
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
    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        try (Connection connection = MySQLConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQLConstants.SELECT_ALL_FROM_PRODUCT);
             ResultSet resultSet = preparedStatement.executeQuery()
        ) {
            while (resultSet.next()){
                products.add(mapProduct(resultSet));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return products;
    }

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
        }
        return product;
    }

    @Override
    public boolean deleteProduct(int productId) {
        PreparedStatement preparedStatement = null;
        try (Connection connection = MySQLConnector.getConnection()){
            preparedStatement = connection.prepareStatement(SQLConstants.DELETE_PRODUCT);
            preparedStatement.setInt(1,productId);
            preparedStatement.executeUpdate();
            return true;
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
        }
        return false;
    }

    @Override
    public Product getProductById(int productId) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try (Connection connection = MySQLConnector.getConnection()){
            preparedStatement = connection.prepareStatement(SQLConstants.SELECT_PRODUCT_BY_ID);
            preparedStatement.setInt(1,productId);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) return mapProduct(resultSet);
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
}
