package dao.impl;

import dao.UserDao;
import entity.Role;
import entity.User;
import util.MySQLConnector;
import util.SQLConstants;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl implements UserDao {

    @Override
    public User createUser(User user) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try (Connection connection = MySQLConnector.getConnection()){
            preparedStatement = connection.prepareStatement(SQLConstants.INSERT_USER, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1,user.getLogin());
            preparedStatement.setString(2,user.getPass());
            preparedStatement.setString(3,user.getRole().toString());
            preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet != null && resultSet.next()){
                user.setId(resultSet.getInt(1));
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
        return user;
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try (Connection connection = MySQLConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQLConstants.SELECT_ALL_FROM_USER);
             ResultSet resultSet = preparedStatement.executeQuery()
        ) {
            while (resultSet.next()){
                users.add(mapUser(resultSet));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return users;
    }

    @Override
    public User updateUser(User user) {
        PreparedStatement preparedStatement = null;
        try (Connection connection = MySQLConnector.getConnection()){
            preparedStatement = connection.prepareStatement(SQLConstants.UPDATE_USER);
            preparedStatement.setString(1, user.getLogin());
            preparedStatement.setString(2,user.getPass());
            preparedStatement.setString(3,user.getRole().toString());
            preparedStatement.setInt(4,user.getId());
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
        return user;
    }

    @Override
    public boolean deleteUser(int userId) {
        PreparedStatement preparedStatement = null;
        try (Connection connection = MySQLConnector.getConnection()){
            preparedStatement = connection.prepareStatement(SQLConstants.DELETE_USER);
            preparedStatement.setInt(1,userId);
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
    public User getUserById(int userId) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try (Connection connection = MySQLConnector.getConnection()){
            preparedStatement = connection.prepareStatement(SQLConstants.SELECT_USER_BY_ID);
            preparedStatement.setInt(1,userId);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) return mapUser(resultSet);
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
    public User getUserByLogin(String login){
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try (Connection connection = MySQLConnector.getConnection()){
            preparedStatement = connection.prepareStatement(SQLConstants.SELECT_USER_BY_LOGIN);
            preparedStatement.setString(1,login);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) return mapUser(resultSet);
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







    private User mapUser(ResultSet result) throws SQLException {
        User user = new User();
        user.setId(result.getInt(SQLConstants.FIELD_USER_ID));
        user.setLogin(result.getString(SQLConstants.FIELD_USER_LOGIN));
        user.setPass(result.getString(SQLConstants.FIELD_USER_PASS));
        user.setRole(Role.valueOf(result.getString(SQLConstants.FIELD_USER_ROLE)));
        return user;
    }
}
