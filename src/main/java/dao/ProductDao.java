package dao;

import entity.Product;

import java.util.List;

public interface ProductDao {
    Product createProduct(Product product);
    Product getProductByName(String name);
    Product getProductByCode(String code);
    List<Product> getAllProducts();
    List<Product> getProductsByReceiptId(int receiptId);
    int getProductsCount();
    int getProductsCountByName(String name);
    int getProductsCountByCode(String code);
    List<Product> getPagingProducts(int page, int size);
    List<Product> getPagingSearchProductsByCode(int page, int size, String code);
    List<Product> getPagingSearchProductsByName(int page, int size, String name);
    Product updateProduct(Product product);
    boolean deleteProduct(int productId);
    Product getProductById(int productId);
}
