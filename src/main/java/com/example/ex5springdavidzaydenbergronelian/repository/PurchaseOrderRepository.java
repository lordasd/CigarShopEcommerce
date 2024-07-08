package com.example.ex5springdavidzaydenbergronelian.repository;

import com.example.ex5springdavidzaydenbergronelian.model.PurchaseOrder;
import com.example.ex5springdavidzaydenbergronelian.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository for PurchaseOrder
 */
@Repository
public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrder, Long> {
    List<PurchaseOrder> findByUser(User user);
}
