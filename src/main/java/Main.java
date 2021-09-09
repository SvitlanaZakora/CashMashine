import dao.ProductDao;
import dao.ReceiptDao;
import dao.UserDao;
import dao.impl.ProductDaoImpl;
import dao.impl.ReceiptDaoImpl;
import dao.impl.UserDaoImpl;
import entity.Product;
import entity.Role;
import entity.User;

public class Main {
    public static void main(String[] args) {
        UserDao userDao = new UserDaoImpl();
        User user = userDao.getUserByLogin("petrov");
        user.setLogin("ivanov");
        System.out.println(userDao.updateUser(user));

        ProductDao productDao = new ProductDaoImpl();
        ReceiptDao receiptDao = new ReceiptDaoImpl();
        Product product = productDao.getProductById(1);
        product.setCapacity(5);
        receiptDao.updateProduct(product,1);
    }
}
