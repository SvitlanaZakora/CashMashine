package dao.impl;

import dao.UserDao;
import entity.Role;
import entity.User;
import org.apache.log4j.Logger;
import util.MySQLConnector;
import util.SQLConstants;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl implements UserDao {

    private static final Logger LOG = Logger.getLogger(UserDaoImpl.class);

    /**
     * Creating new user in database.
     *
     * @param user User with info
     * @return created entity from database
     */
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
            LOG.info("Connection installed successfully" );
            resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet != null && resultSet.next()){
                LOG.info("User created successfully" );
                user.setId(resultSet.getInt(1));
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
        return user;
    }


    /**
     * Receiving user by login and current language.
     *
     * @param login Login for searching user
     * @param lang Current language on page
     * @return user from database where login is equal to param
     */
    @Override
    public User getUserByLogin(String login, String lang){
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try (Connection connection = MySQLConnector.getConnection()){
            preparedStatement = connection.prepareStatement(SQLConstants.SELECT_USER_BY_LOGIN_WITH_LANG);
            preparedStatement.setString(1,login);
            preparedStatement.setString(2,lang);
            resultSet = preparedStatement.executeQuery();
            LOG.info("Connection installed successfully" );
            if (resultSet.next()) {
                LOG.info("User by login received successfully");
                return mapUser(resultSet);
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
     * Receving all user`s roles by current language.
     *
     * @param lang Current language on page
     * @return list of roles where language is equal to param
     */
    public List<Role> getAllRolesByLang(String lang){
        List<Role> roles = new ArrayList<>();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try (Connection connection = MySQLConnector.getConnection()){
            preparedStatement = connection.prepareStatement(SQLConstants.SELECT_ALL_ROLES_BY_LANG);
            preparedStatement.setString(1,lang);
            resultSet = preparedStatement.executeQuery();
            LOG.info("Connection installed successfully" );
            while (resultSet.next()){
                roles.add(mapRole(resultSet));
            }
            LOG.info("All Roles By Lang received successfully");
            return roles;
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
     * Receiving user by user_id.
     *
     * @param userId User_id for searching user
     * @param lang Current language on page
     * @return user where id and language is equal to param
     */
    @Override
    public User getUserById(int userId, String lang) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try (Connection connection = MySQLConnector.getConnection()){
            preparedStatement = connection.prepareStatement(SQLConstants.SELECT_USER_BY_ID_WITH_LANG);
            preparedStatement.setInt(1,userId);
            preparedStatement.setString(2,lang);
            resultSet = preparedStatement.executeQuery();
            LOG.info("Connection installed successfully" );
            if (resultSet.next()){
                LOG.info("User by id received successfully" );
                return mapUser(resultSet);
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
     * Mapping user_role from resultSet.
     *
     * @param result ResultSet that received from executeQuery
     * @return entity Role
     * @throws SQLException if a database access failed
     */
    private Role mapRole(ResultSet result) throws SQLException{
        Role role = new Role();
        role.setId(result.getInt(SQLConstants.FIELD_ROLE_ID));
        role.setShort_name(result.getString(SQLConstants.FIELD_ROLE_SHORT_NAME));
        role.setName(result.getString(SQLConstants.FIELD_ROLE_NAME));
        return role;
    }


    /**
     * Mapping user from resultSet.
     *
     * @param result ResultSet that received from executeQuery
     * @return entity User
     * @throws SQLException if a database access failed
     */
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
