package service.impl;

import dao.ProductDao;
import dao.impl.ProductDaoImpl;
import entity.Product;
import service.ProductService;

import java.util.List;

public class ProductServiceImpl implements ProductService {

    ProductDao productDao;

    public ProductServiceImpl() {
        productDao = new ProductDaoImpl();
    }

    @Override
    public Product createProduct(Product product) {
        return productDao.createProduct(product);
    }

    @Override
    public Product getProductByName(String name) {
        return productDao.getProductByName(name);
    }

    @Override
    public List<Product> getAllProducts() {
        return productDao.getAllProducts();
    }

    @Override
    public List<Product> getProductsByReceiptId(int receiptId) {
        return productDao.getProductsByReceiptId(receiptId);
    }

    @Override
    public int getProductsCount() {
        return productDao.getProductsCount();
    }

    @Override
    public int getProductsCountByName(String name) {
        return productDao.getProductsCountByName(name);
    }

    @Override
    public int getProductsCountByCode(String code) {
        return productDao.getProductsCountByCode(code);
    }

    @Override
    public List<Product> getPagingProducts(int page, int size) {
        return productDao.getPagingProducts(page, size);
    }

    @Override
    public List<Product> getPagingSearchProductsByCode(int page, int size, String code) {return productDao.getPagingSearchProductsByCode(page,size,code);}

    @Override
    public List<Product> getPagingSearchProductsByName(int page, int size, String name) {return productDao.getPagingSearchProductsByName(page,size,name);}

    @Override
    public Product updateProduct(Product product) {
        return productDao.updateProduct(product);
    }

    @Override
    public boolean deleteProduct(int productId) {
        return productDao.deleteProduct(productId);
    }

    @Override
    public Product getProductById(int productId) {
        return productDao.getProductById(productId);
    }

    @Override
    public Product getProductByCode(String code) {
        return productDao.getProductByCode(code);
    }
}
