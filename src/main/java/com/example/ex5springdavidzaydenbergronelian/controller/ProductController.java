package com.example.ex5springdavidzaydenbergronelian.controller;

import com.example.ex5springdavidzaydenbergronelian.model.Product;
import com.example.ex5springdavidzaydenbergronelian.model.Review;
import com.example.ex5springdavidzaydenbergronelian.service.ProductService;
import com.example.ex5springdavidzaydenbergronelian.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Controller for handling product-related requests.
 */
@Controller
@RequestMapping("/product")
public class ProductController {
    private final ProductService productService;
    private final ReviewService reviewService;

    /**
     * Constructor for ProductController.
     * @param productService The ProductService to be injected into the controller.
     * @param reviewService The ReviewService to be injected into the controller.
     */
    @Autowired
    public ProductController(ProductService productService, ReviewService reviewService) {
        this.productService = productService;
        this.reviewService = reviewService;
    }

    /**
     * Displays the details of a product.
     * @param id The ID of the product to be displayed.
     * @param model The model to be passed to the view.
     * @return The name of the view to be displayed.
     */
    @GetMapping("/{id}")
    public String viewProduct(@PathVariable Long id, Model model) {
        Product product = productService.findById(id);
        List<Review> reviews = reviewService.findByProduct(product);

        model.addAttribute("product", product);
        model.addAttribute("reviews", reviews);

        return "product-details";
    }

    /**
     * Searches for products based on a search query.
     * @param search The search query to be used.
     * @param model The model to be passed to the view.
     * @return The name of the view to be displayed.
     */
    @GetMapping("/search")
    public String searchProducts(@RequestParam(required = false) String search, Model model) {
        List<Product> products;
        if (search == null || search.isEmpty()) {
            products = productService.getAllProducts();
        } else {
            products = productService.searchProducts(search);
        }
        model.addAttribute("products", products);
        return "fragments/product-table :: productList";
    }

    /**
     * Filters products based on a category.
     * @param category The category to be used for filtering.
     * @param model The model to be passed to the view.
     * @return The name of the view to be displayed.
     */
    @GetMapping("/filter")
    public String filterProducts(@RequestParam(required = false) String category, Model model) {
        List<Product> products;
        if (category == null || category.isEmpty())
            products = productService.getAllProducts();
        else
            products = productService.filterProductsByCategory(category);

        model.addAttribute("products", products);
        return "fragments/product-table :: productList";
    }

    /**
     * Lists all products.
     * @param model The model to be passed to the view.
     * @return The name of the view to be displayed.
     */
    @GetMapping("/list")
    public String listProducts(Model model) {
        List<Product> products = productService.getAllProducts();
        model.addAttribute("products", products);
        return "fragments/product-table :: productList";
    }
}