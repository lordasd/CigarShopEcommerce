package com.example.ex5springdavidzaydenbergronelian.service;

import com.example.ex5springdavidzaydenbergronelian.model.Product;
import com.example.ex5springdavidzaydenbergronelian.model.Review;
import com.example.ex5springdavidzaydenbergronelian.model.User;
import com.example.ex5springdavidzaydenbergronelian.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service class for Review
 */
@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    /**
     * Find all reviews
     * @return List of all reviews
     */
    public List<Review> findByProduct(Product product) {
        return reviewRepository.findByProduct(product);
    }

    /**
     * Find review by id
     * @param id Review id
     * @return Review
     */
    public Optional<Review> findById(Long id) {
        return reviewRepository.findById(id);
    }

    /**
     * Find review by product and user
     * @param product Product
     * @param user User
     * @return Review
     */
    public Optional<Review> findByProductAndUser(Product product, User user) {
        return reviewRepository.findByProductAndUser(product, user);
    }

    /**
     * Save review
     * @param review Review
     */
    public void saveReview(Review review) {
        reviewRepository.save(review);
    }

    /**
     * Delete review
     * @param id Review id
     */
    public void deleteReview(Long id) {
        reviewRepository.deleteById(id);
    }

    /**
     * Find reviews by user
     * @param user User
     * @return List of reviews
     */
    public List<Review> findByUser(User user) {
        return reviewRepository.findByUser(user);
    }
}
