package service;

import entity.Product;
import entity.Receipt;

public interface ReceiptService {
    public Receipt create(int userId);
    public boolean addProductToReceipt(int productId, int receiptId, double capacity);
    public boolean updateProduct(int productId, int receiptId, double capacity);
    public boolean deleteProduct(int productId, int receiptId);
    public boolean deleteById(int id);
    public Receipt getById(int receiptId);
    public boolean close(int receiptId);
}
