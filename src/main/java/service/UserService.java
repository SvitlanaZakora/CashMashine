package service;

import dao.UserDao;
import entity.User;

import java.util.List;

public interface UserService extends UserDao {
    public User createUser(User user);
    public User getUserByLogin(String login);
    public List<User> getAllUsers();
    public User updateUser(User user);
    public boolean deleteUser(int userId);
}
