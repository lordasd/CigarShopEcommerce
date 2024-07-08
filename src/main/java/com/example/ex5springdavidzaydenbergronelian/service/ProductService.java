package com.example.ex5springdavidzaydenbergronelian.service;

import com.example.ex5springdavidzaydenbergronelian.model.Product;
import com.example.ex5springdavidzaydenbergronelian.repository.OrderProductRepository;
import com.example.ex5springdavidzaydenbergronelian.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.ex5springdavidzaydenbergronelian.constants.ErrorMessages.CANNOT_DELETE_PRODUCT_WITH_EXISTING_ORDERS;

/**
 * Service class for managing products.
 */
@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderProductRepository orderProductRepository;

    /**
     * Get all products.
     *
     * @return List of all products.
     */
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    /**
     * Get product by id.
     *
     * @param id Product id.
     * @return Product with the given id.
     */
    public Product findById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    /**
     * Get products with low stock.
     *
     * @param threshold Stock threshold.
     * @return List of products with stock quantity less than the threshold.
     */
    public List<Product> findLowStockProducts(int threshold) {
        return productRepository.findLowStockProducts(threshold);
    }

    /**
     * Delete product by id.
     *
     * @param id Product id.
     */
    public void deleteById(Long id) {
        if (!orderProductRepository.existsByProductId(id))
            productRepository.deleteById(id);
        else
            throw new IllegalArgumentException(CANNOT_DELETE_PRODUCT_WITH_EXISTING_ORDERS);
    }

    /**
     * Save product.
     *
     * @param product Product to save.
     */
    public void save(Product product) {
        productRepository.save(product);
    }

    /**
     * Find all products.
     * @return List of all products.
     */
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public void saveOrUpdate(Product product) {
        Product existingProduct = productRepository.findById(product.getId()).orElse(null);
        if (existingProduct != null) {
            existingProduct.setName(product.getName());
            existingProduct.setBrand(product.getBrand());
            existingProduct.setCategory(product.getCategory());
            existingProduct.setDescription(product.getDescription());
            existingProduct.setPrice(product.getPrice());
            existingProduct.setStockQuantity(product.getStockQuantity());
            productRepository.save(existingProduct);
        } else
            productRepository.save(product);
    }

    /**
     * Search products by name.
     *
     * @param search Search query.
     * @return List of products with names containing the search query.
     */
    public List<Product> searchProducts(String search) {
        return productRepository.findByNameContainingIgnoreCase(search);
    }

    /**
     * Filter products by category.
     *
     * @param category Category to filter by.
     * @return List of products with the given category.
     */
    public List<Product> filterProductsByCategory(String category) {
        if (category == null || category.isEmpty())
            return productRepository.findAll();
        return productRepository.findByCategory(category);
    }
}
