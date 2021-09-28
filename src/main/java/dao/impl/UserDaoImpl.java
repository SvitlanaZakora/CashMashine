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
            preparedStatement.setInt(3,user.getRole().getId());
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
    public User getUserByLogin(String login, String lang){
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try (Connection connection = MySQLConnector.getConnection()){
            preparedStatement = connection.prepareStatement(SQLConstants.SELECT_USER_BY_LOGIN_WITH_LANG);
            preparedStatement.setString(1,login);
            preparedStatement.setString(2,lang);
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

    public List<Role> getAllRolesByLang(String lang){
        List<Role> roles = new ArrayList<>();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try (Connection connection = MySQLConnector.getConnection()){
            preparedStatement = connection.prepareStatement(SQLConstants.SELECT_ALL_ROLES_BY_LANG);
            preparedStatement.setString(1,lang);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                roles.add(mapRole(resultSet));
            }
            return roles;
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
    public User getUserById(int userId, String lang) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try (Connection connection = MySQLConnector.getConnection()){
            preparedStatement = connection.prepareStatement(SQLConstants.SELECT_USER_BY_ID_WITH_LANG);
            preparedStatement.setInt(1,userId);
            preparedStatement.setString(2,lang);
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


    private Role mapRole(ResultSet result) throws SQLException{
        Role role = new Role();
        role.setId(result.getInt(SQLConstants.FIELD_ROLE_ID));
        role.setShort_name(result.getString(SQLConstants.FIELD_ROLE_SHORT_NAME));
        role.setName(result.getString(SQLConstants.FIELD_ROLE_NAME));
        return role;
    }



    private User mapUser(ResultSet result) throws SQLException {
        User user = new User();
        user.setId(result.getInt(SQLConstants.FIELD_USER_ID));
        user.setLogin(result.getString(SQLConstants.FIELD_USER_LOGIN));
        user.setPass(result.getString(SQLConstants.FIELD_USER_PASS));
        user.setRole(new Role());
        user.getRole().setId(result.getInt(SQLConstants.FIELD_ROLE_ID));
        user.getRole().setShort_name(result.getString(SQLConstants.FIELD_ROLE_SHORT_NAME));
        user.getRole().setName(result.getString(SQLConstants.FIELD_ROLE_NAME));
        return user;
    }
}
