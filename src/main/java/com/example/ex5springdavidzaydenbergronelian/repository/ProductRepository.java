package com.example.ex5springdavidzaydenbergronelian.repository;

import com.example.ex5springdavidzaydenbergronelian.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository for Product
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findByProductId(String productId);

    @Query("SELECT p FROM Product p WHERE p.stockQuantity < ?1")
    List<Product> findLowStockProducts(int threshold);

    List<Product> findByNameContainingIgnoreCase(String search);

    List<Product> findByCategory(String category);
}
