package service.impl;

import dao.ProductDao;
import dao.ReceiptDao;
import dao.impl.ProductDaoImpl;
import dao.impl.ReceiptDaoImpl;
import entity.Product;
import entity.Receipt;
import service.ReceiptService;

public class ReceiptServiceImpl implements ReceiptService {

    ReceiptDao receiptDao;
    ProductDao productDao;

    public ReceiptServiceImpl() {
        receiptDao = new ReceiptDaoImpl();
        productDao = new ProductDaoImpl();
    }

    @Override
    public Receipt create(int userId) {
        return receiptDao.create(userId);
    }

    @Override
    public boolean addProductToReceipt(int productId, int receiptId, double capacity) {
        Product product = productDao.getProductById(productId);
        product.setCapacity(capacity);
        return receiptDao.addProductToReceipt(product,receiptId);
    }

    @Override
    public boolean updateProduct(int productId, int receiptId, double capacity) {
        Product product = productDao.getProductById(productId);
        product.setCapacity(capacity);
        return receiptDao.updateProduct(product,receiptId);
    }

    @Override
    public boolean deleteProduct(int productId, int receiptId) {
        return false;
    }

    @Override
    public boolean deleteById(int id) {
        return false;
    }

    @Override
    public Receipt getById(int receiptId) {
        return null;
    }

    @Override
    public boolean close(int receiptId) {
        return false;
    }
}
