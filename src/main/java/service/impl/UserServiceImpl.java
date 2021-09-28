package service.impl;

import dao.UserDao;
import dao.impl.UserDaoImpl;
import entity.Role;
import entity.User;
import service.UserService;

import java.util.List;

public class UserServiceImpl implements UserService {

    UserDao userDao;

    public UserServiceImpl() {
        userDao = new UserDaoImpl();
    }

    @Override
    public User createUser(User user) {
        return userDao.createUser(user);
    }

    @Override
    public User getUserByLogin(String login, String lang) {
        return userDao.getUserByLogin(login,lang);
    }

    @Override
    public List<Role> getAllRolesByLang(String lang) {
        return userDao.getAllRolesByLang(lang);
    }

    @Override
    public User getUserById(int userId, String lang) {
        return userDao.getUserById(userId,lang);
    }
}
