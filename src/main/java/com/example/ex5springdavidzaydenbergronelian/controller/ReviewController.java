package com.example.ex5springdavidzaydenbergronelian.controller;

import com.example.ex5springdavidzaydenbergronelian.model.Product;
import com.example.ex5springdavidzaydenbergronelian.model.Review;
import com.example.ex5springdavidzaydenbergronelian.model.User;
import com.example.ex5springdavidzaydenbergronelian.service.ProductService;
import com.example.ex5springdavidzaydenbergronelian.service.ReviewService;
import com.example.ex5springdavidzaydenbergronelian.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;

import static com.example.ex5springdavidzaydenbergronelian.constants.ErrorMessages.ALREADY_REVIEWED_PRODUCT;
import static com.example.ex5springdavidzaydenbergronelian.constants.ErrorMessages.REVIEW_NOT_FOUND;

/**
 * Controller for handling review-related requests.
 */
@Controller
@RequestMapping("/reviews")
public class ReviewController {
    private final ReviewService reviewService;
    private final ProductService productService;
    private final UserService userService;

    /**
     * Constructor for ReviewController.
     * @param reviewService reviewService
     * @param productService productService
     * @param userService userService
     */
    @Autowired
    public ReviewController(ReviewService reviewService, ProductService productService, UserService userService) {
        this.reviewService = reviewService;
        this.productService = productService;
        this.userService = userService;
    }

    /**
     * Add a review for a product.
     * @param userDetails userDetails
     * @param productId productId
     * @param rating rating
     * @param comment comment
     * @param model model
     * @return redirect to the product page
     */
    @PostMapping("/add")
    public String addReview(@AuthenticationPrincipal UserDetails userDetails,
                            @RequestParam Long productId,
                            @RequestParam int rating,
                            @RequestParam String comment,
                            Model model) {
        User user = userService.findByUsername(userDetails.getUsername());
        Product product = productService.findById(productId);

        // Check if the user has already reviewed this product
        Optional<Review> existingReview = reviewService.findByProductAndUser(product, user);
        if (existingReview.isPresent()) {
            model.addAttribute("errorMessage", ALREADY_REVIEWED_PRODUCT);
            return "redirect:/product/" + productId + "?error=alreadyReviewed";
        }

        Review review = new Review();
        review.setProduct(product);
        review.setUser(user);
        review.setRating(rating);
        review.setComment(comment);
        review.setReviewDate(LocalDateTime.now());

        reviewService.saveReview(review);

        return "redirect:/product/" + productId;
    }

    /**
     * Edit a review.
     * @param reviewId reviewId
     * @param rating rating
     * @param comment comment
     * @param model model
     * @return redirect to the product page
     */
    @PostMapping("/edit/{reviewId}")
    public String editReview(@PathVariable Long reviewId,
                             @RequestParam int rating,
                             @RequestParam String comment,
                             Model model) {
        Review review = reviewService.findById(reviewId).orElseThrow(() -> new RuntimeException(REVIEW_NOT_FOUND));

        review.setRating(rating);
        review.setComment(comment);
        review.setReviewDate(LocalDateTime.now());

        reviewService.saveReview(review);

        return "redirect:/product/" + review.getProduct().getId();
    }

    /**
     * Delete a review.
     * @param userDetails userDetails
     * @param reviewId reviewId
     * @return redirect to the product page
     */
    @PostMapping("/delete/{reviewId}")
    public String deleteReview(@AuthenticationPrincipal UserDetails userDetails,
                               @PathVariable Long reviewId) {
        Review review = reviewService.findById(reviewId).orElseThrow(() -> new RuntimeException(REVIEW_NOT_FOUND));
        User user = userService.findByUsername(userDetails.getUsername());

        // Allow deletion if the user is an admin or the owner of the review
        if (user.getRole().equals("ROLE_ADMIN") || review.getUser().equals(user))
            reviewService.deleteReview(reviewId);

        return "redirect:/product/" + review.getProduct().getId();
    }
}
