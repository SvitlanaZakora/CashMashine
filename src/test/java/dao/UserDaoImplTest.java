package dao;

import dao.impl.ReceiptDaoImpl;
import dao.impl.UserDaoImpl;
import entity.Receipt;
import entity.Role;
import entity.RoleShortNames;
import entity.User;
import org.junit.jupiter.api.*;
import util.MySQLConnector;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImplTest {

    private static UserDaoImpl userDao;
    private static Connection con;

    @BeforeAll
    public static void init(){
        userDao = new UserDaoImpl();
    }

    @AfterEach
    public void after() throws SQLException, IOException, ClassNotFoundException {
        con = MySQLConnector.getConnection();
        StringBuilder stringBuilder = new StringBuilder();
        List<String> sql = Files.readAllLines(Paths.get("src/test/resources/dbClean.sql"));

        PreparedStatement preparedStatement = null;
        try (Connection connection = MySQLConnector.getConnection()){
            for (String s : sql){
                if(s.length()==0){
                    preparedStatement = connection.prepareStatement(stringBuilder.toString());
                    preparedStatement.executeUpdate();
                    stringBuilder=new StringBuilder();
                    continue;
                }
                stringBuilder.append(s);
            }
            preparedStatement = connection.prepareStatement(stringBuilder.toString());
            preparedStatement.executeUpdate();
        } catch (Exception ignored) {
            System.out.println();
        }
    }
    @BeforeEach
    public void before() throws IOException, SQLException, ClassNotFoundException {
        con = MySQLConnector.getConnection();
        StringBuilder stringBuilder = new StringBuilder();
        List<String>  sql = Files.readAllLines(Paths.get("src/test/resources/db.sql"));

        PreparedStatement preparedStatement = null;
        try (Connection connection = MySQLConnector.getConnection()){
            for (String s : sql){
                if(s.length()==0){
                    preparedStatement = connection.prepareStatement(stringBuilder.toString());
                    preparedStatement.executeUpdate();
                    stringBuilder=new StringBuilder();
                    continue;
                }
                stringBuilder.append(s);
            }
            preparedStatement = connection.prepareStatement(stringBuilder.toString());
            preparedStatement.executeUpdate();
        } catch (Exception ignored) {
            System.out.println();
        }
    }

    @Test
    public void createUserTest(){
        User user = new User(4,"four","four", new Role(1, "C", "CASHIER"));
        User userActual = userDao.createUser(user);
        Assertions.assertEquals(user,userActual);
    }

    @Test
    public void getUserByLoginTest(){
        User user = new User(1,"fir","fir", new Role(1, "C", "CASHIER"));
        User userActual = userDao.getUserByLogin("fir","en");
        Assertions.assertEquals(user,userActual);
    }

    @Test
    public void getAllRolesByLangTest(){
        Role role1 = new Role(1, "C", "CASHIER");
        Role role2 = new Role(2, "SC", "Senior");
        Role role3 = new Role(3, "CE", "Expert");
        List<Role> rolesList = new ArrayList<>();
        rolesList.add(role1);
        rolesList.add(role2);
        rolesList.add(role3);
        List<Role> rolesListActual = userDao.getAllRolesByLang("en");
        Assertions.assertEquals(rolesList,rolesListActual);
    }

    @Test
    public void getUserByIdTest(){
        User user = new User(1,"fir","fir", new Role(1, "C", "CASHIER"));
        User userActual = userDao.getUserById(1,"en");
        Assertions.assertEquals(user,userActual);
    }


}
