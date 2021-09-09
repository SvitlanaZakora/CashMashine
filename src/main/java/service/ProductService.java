package service;

import entity.Product;

import java.util.List;

public interface ProductService {
    public Product createProduct(Product product);
    public Product getProductByName(String name);
    public List<Product> getAllProducts();
    public Product updateProduct(Product product);
    public boolean deleteProduct(int productId);
    public Product getProductById(int productId);
}
