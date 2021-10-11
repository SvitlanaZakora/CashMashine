package service.impl;

import dao.ProductDao;
import dao.ReceiptDao;
import dao.impl.ProductDaoImpl;
import dao.impl.ReceiptDaoImpl;
import entity.Product;
import entity.Receipt;
import service.ReceiptService;

import java.time.LocalDateTime;
import java.util.List;

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
        return receiptDao.deleteProduct(productId,receiptId);
    }

    @Override
    public boolean deleteById(int id) {
        return receiptDao.deleteById(id);
    }

    @Override
    public Receipt getById(int receiptId) {
        return receiptDao.getById(receiptId);
    }

    @Override
    public Receipt getByIdWithOwner(int receiptId) {
        return receiptDao.getByIdWithOwner(receiptId);
    }

    @Override
    public List<Receipt> getPagingReceipts(int page, int size) {
        return receiptDao.getPagingReceipts(page,size);
    }

    @Override
    public int getReceiptsCount() {
        return receiptDao.getReceiptsCount();
    }

    @Override
    public List<Receipt> getAllReceiptsByUserIdAndActive(int userId, boolean active) {
        return receiptDao.getAllReceiptsByUserIdAndActive(userId, active);
    }


    @Override
    public boolean close(int receiptId, double total) {
        return receiptDao.close(receiptId,total);
    }

    @Override
    public List<Receipt> getAllClosedReceiptsBetweenDate(LocalDateTime localDateTime1, LocalDateTime localDateTime2) {
        return receiptDao.getAllClosedReceiptsBetweenDate(localDateTime1,localDateTime2);
    }
}
