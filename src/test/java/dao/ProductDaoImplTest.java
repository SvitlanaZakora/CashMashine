package dao;

import dao.impl.ProductDaoImpl;
import dao.impl.ReceiptDaoImpl;
import dao.impl.UserDaoImpl;
import entity.Product;
import org.junit.jupiter.api.*;
import util.MySQLConnector;
import util.SQLConstants;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDaoImplTest {

    private static ProductDaoImpl productDao;
    private static Connection con;

    @BeforeAll
    public static void init(){
        productDao = new ProductDaoImpl();
    }

    @AfterEach
    public void after() throws SQLException, IOException, ClassNotFoundException {
        con = MySQLConnector.getConnection();
        StringBuilder stringBuilder = new StringBuilder();
        List<String>  sql = Files.readAllLines(Paths.get("src/test/resources/dbClean.sql"));

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
    public void createProductTest(){
        Product product = new Product("1","T","kg",20.0,20.5);
        Product productActual = productDao.createProduct(product);

        Assertions.assertEquals(product,productActual);
    }

    @Test
    public void getProductByNameTest(){
        Product product = new Product("111","tomato","kg",20.1,10.0);
        product.setId(1);

        Product productActual = productDao.getProductByName("tomato");

        Assertions.assertEquals(product,productActual);

    }

    @Test
    public void getProductByCodeTest(){
        Product product = new Product("111","tomato","kg",20.1,10.0);
        Product productActual = productDao.getProductByCode("111");

        product.setId(1);
        Assertions.assertEquals(product,productActual);


    }

    @Test
    public void getAllProductsTest(){
        Product product1 = new Product("111","tomato","kg",20.1,10.0);
        Product product2 = new Product("222","milk","psc",10.0,20.0);
        product1.setId(1);
        product2.setId(2);
        List<Product> productList = new ArrayList<>();
        productList.add(product1);
        productList.add(product2);

        List<Product> productListActual = productDao.getAllProducts();

        Assertions.assertEquals(productList,productListActual);

    }

    @Test
    public void getProductsByReceiptIdTest(){

        Product product1 = new Product("111","tomato","kg",2,20.0);
        product1.setId(1);

        List<Product> productList = new ArrayList<>();
        productList.add(product1);

        List<Product> productListActual = productDao.getProductsByReceiptId(1);

        Assertions.assertEquals(productList,productListActual);

    }

    @Test
    public void getProductsCountTest(){

        int productsCount = productDao.getProductsCount();

        Assertions.assertEquals(2,productsCount);

    }

    @Test
    public void getProductsCountByNameTest(){

        int productsCountByName = productDao.getProductsCountByName("tomato");

        Assertions.assertEquals(1, productsCountByName);

    }

    @Test
    public void getProductsCountByCodeTest(){

        int productsCountByCode = productDao.getProductsCountByCode("111");

        Assertions.assertEquals(1,productsCountByCode);

    }

    @Test
    public void getPagingProductsTest(){

        List<Product> productsListByPaging1 = productDao.getPagingProducts(0,1);
        int productsCountByPagingActual1 = productsListByPaging1.size();

        Assertions.assertEquals(1,productsCountByPagingActual1);

        List<Product> productsListByPaging2 = productDao.getPagingProducts(0,2);
        int productsCountByPagingActual2 = productsListByPaging2.size();

        Assertions.assertEquals(2,productsCountByPagingActual2);

    }

    @Test
    public void getPagingSearchProductsByCodeTest(){

        List<Product> productsListByPagingByCode1 = productDao.getPagingSearchProductsByCode(0,1,"111");
        int productsCountByPagingByCodeActual1 = productsListByPagingByCode1.size();

        Assertions.assertEquals(1,productsCountByPagingByCodeActual1);

        List<Product> productsListByPagingByCode2 = productDao.getPagingSearchProductsByCode(0,2,"111");
        int productsCountByPagingByCodeActual2 = productsListByPagingByCode2.size();

        Assertions.assertEquals(1,productsCountByPagingByCodeActual2);

    }

    @Test
    public void getPagingSearchProductsByNameTest(){

        List<Product> productsListByPagingByName1 = productDao.getPagingSearchProductsByName(0,1,"tomato");
        int productsCountByPagingByNameActual1 = productsListByPagingByName1.size();

        Assertions.assertEquals(1,productsCountByPagingByNameActual1);

        List<Product> productsListByPagingByName2 = productDao.getPagingSearchProductsByName(0,2,"tomato");
        int productsCountByPagingByNameActual2 = productsListByPagingByName2.size();

        Assertions.assertEquals(1,productsCountByPagingByNameActual2);

    }

    @Test
    public void updateProductTest(){
        Product product1 = productDao.getProductById(1);
        product1.setPrice(15.0);

        Product productActual = productDao.updateProduct(product1);

        Assertions.assertEquals(product1, productActual);

    }

    @Test
    public void deleteProductTest(){
        Product product1 = productDao.getProductById(1);

        boolean actual = productDao.deleteProduct(1);

        Assertions.assertEquals(true,actual);

    }

    @Test
    public void getProductByIdTest(){
        Product product1 = new Product("111","tomato","kg",20.1,10.0);
        product1.setId(1);

        Product productActual = productDao.getProductById(1);

        Assertions.assertEquals(product1,productActual);

    }

}
