package com.example.ex5springdavidzaydenbergronelian.repository;

import com.example.ex5springdavidzaydenbergronelian.model.OrderProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for OrderProduct
 */
@Repository
public interface OrderProductRepository extends JpaRepository<OrderProduct, Long> {
    boolean existsByProductId(Long id);
}
