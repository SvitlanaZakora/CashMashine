package dao;

import entity.Role;
import entity.User;

import java.util.List;

public interface UserDao {
    public User createUser(User user);
    public User getUserByLogin(String login, String lang);
    public List<Role> getAllRolesByLang(String lang);
    public User getUserById(int userId, String lang);

}
