package service;

import dao.UserDao;
import entity.Role;
import entity.User;

import java.util.List;

public interface UserService extends UserDao {
    User createUser(User user);
    User getUserByLogin(String login, String lang);
    List<Role> getAllRolesByLang(String lang);
    User getUserById(int userId, String lang);
}
