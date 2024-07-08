package com.example.ex5springdavidzaydenbergronelian.repository;

import com.example.ex5springdavidzaydenbergronelian.model.Product;
import com.example.ex5springdavidzaydenbergronelian.model.Review;
import com.example.ex5springdavidzaydenbergronelian.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository for Review
 */
@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByProduct(Product product);
    Optional<Review> findByProductAndUser(Product product, User user);

    List<Review> findByUser(User user);
}
