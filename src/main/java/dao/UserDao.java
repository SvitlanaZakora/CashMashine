package dao;

import entity.Role;
import entity.User;

import java.util.List;

public interface UserDao {
    public User createUser(User user);
    public User getUserByLogin(String login);
    public List<User> getAllUsers();
    public User updateUser(User user);
    public boolean deleteUser(int userId);
    public User getUserById(int userId);

}
