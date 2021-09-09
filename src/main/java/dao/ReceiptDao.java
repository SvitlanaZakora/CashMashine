package dao;

import entity.Product;
import entity.Receipt;

public interface ReceiptDao {
    public Receipt create(int userId);
    public boolean addProductToReceipt(Product product, int receiptId);
    public boolean updateProduct(Product product, int receiptId);
    public boolean deleteProduct(int productId, int receiptId);
    public boolean deleteById(int id);
    public Receipt getById(int receiptId);
    public boolean close(int receiptId);

}
