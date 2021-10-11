package dao;

import dao.impl.ProductDaoImpl;
import dao.impl.ReceiptDaoImpl;
import entity.Product;
import entity.Receipt;
import org.junit.jupiter.api.*;
import util.MySQLConnector;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class ReceiptDaoImplTest {

    private static ReceiptDaoImpl receiptDao;
    private static Connection con;

    @BeforeAll
    public static void init(){
        receiptDao = new ReceiptDaoImpl();
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
    public void createReceiptTest(){
        Receipt receiptActual = receiptDao.create(1);
        Assertions.assertNotNull(receiptActual);
    }

    @Test
    public void closeTest(){
        boolean actual = receiptDao.close(1,20.0);
        Assertions.assertTrue(actual);
    }

    @Test
    public void addProductToReceiptTest(){
        Product product = new Product("222","milk","psc",2,40.0);
        product.setId(2);
        boolean actual = receiptDao.addProductToReceipt(product,1);
        Assertions.assertTrue(actual);
    }

    @Test
    public void updateProductTest(){
        Product product = new Product("111","tomato","kg",2,20.0);
        product.setCapacity(3.0);
        boolean actual = receiptDao.updateProduct(product,1);
        Assertions.assertTrue(actual);
    }

    @Test
    public void deleteProductTest(){
        boolean actual = receiptDao.deleteProduct(1,1);
        Assertions.assertTrue(actual);
    }

    @Test
    public void deleteByIdTest(){
        boolean actual = receiptDao.deleteById(1);
        Assertions.assertTrue(actual);
    }

    @Test
    public void getByIdTest(){
        Receipt receiptActual = receiptDao.getById(1);
        Assertions.assertNotNull(receiptActual);
    }

    @Test
    public void getByIdWithOwnerTest(){
        Receipt receiptActual = receiptDao.getByIdWithOwner(1);
        Assertions.assertNotNull(receiptActual);
    }

    @Test
    public void getPagingReceiptsTest(){
        List<Receipt> receiptList = receiptDao.getPagingReceipts(0,1);
        int receiptsSizeActual = receiptList.size();
        Assertions.assertEquals(1,receiptsSizeActual);
    }

    @Test
    public void getReceiptsCountTest(){
        int receiptsCount = receiptDao.getReceiptsCount();

        Assertions.assertEquals(2,receiptsCount);
    }

    @Test
    public void getAllReceiptsByUserIdAndActiveTest(){

        List<Receipt> receiptsListExpected = receiptDao.getAllReceiptsByUserIdAndActive(1,true);
        int receiptsSizeActual = receiptsListExpected.size();

        Assertions.assertEquals(1,receiptsSizeActual);
    }

    @Test
    public void getAllClosedReceiptsBetweenDateTest(){
        LocalDate localDate = LocalDate.of(2021,10,3);
        LocalDateTime localDateTime1 = localDate.atStartOfDay().plusSeconds(1);
        LocalDateTime localDateTime2 = localDate.atStartOfDay().plusDays(1).minusSeconds(1);

        List<Receipt> receiptsListActual = receiptDao.getAllClosedReceiptsBetweenDate(localDateTime1,localDateTime2);
        int receiptsSizeActual = receiptsListActual.size();

        Assertions.assertEquals(1,receiptsSizeActual);
    }


}
