package service;

import entity.Receipt;

import java.time.LocalDateTime;
import java.util.List;

public interface ReceiptService {
    Receipt create(int userId);
    boolean addProductToReceipt(int productId, int receiptId, double capacity);
    boolean updateProduct(int productId, int receiptId, double capacity);
    boolean deleteProduct(int productId, int receiptId);
    boolean deleteById(int id);
    Receipt getById(int receiptId);
    Receipt getByIdWithOwner(int receiptId);
    List<Receipt> getPagingReceipts(int page, int size);
    int getReceiptsCount();
    List<Receipt> getAllReceiptsByUserIdAndActive(int userId, boolean active);
    boolean close(int receiptId, double total);
    List<Receipt> getAllClosedReceiptsBetweenDate(LocalDateTime localDateTime1, LocalDateTime localDateTime2);
}
