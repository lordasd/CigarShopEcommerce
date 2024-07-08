package com.example.ex5springdavidzaydenbergronelian.controller;

import com.example.ex5springdavidzaydenbergronelian.model.Product;
import com.example.ex5springdavidzaydenbergronelian.service.CartService;
import com.example.ex5springdavidzaydenbergronelian.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Controller for handling cart-related requests.
 */
@Controller
@RequestMapping("/cart")
public class CartController {
    private final CartService cartService;
    private final ProductService productService;

    /**
     * Constructor for CartController.
     * @param cartService CartService
     * @param productService ProductService
     */
    @Autowired
    public CartController(CartService cartService, ProductService productService) {
        this.cartService = cartService;
        this.productService = productService;
    }

    /**
     * Add a product to the cart.
     * @param productId Product ID
     * @param quantity Quantity
     * @return ResponseEntity<Map<String, Object>>
     */
    @PostMapping("/add")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> addToCart(@RequestParam Long productId, @RequestParam int quantity) {
        Product product = productService.findById(productId);
        cartService.addProduct(product, quantity);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Item added to cart successfully!");
        response.put("cartSize", cartService.getTotalQuantity());
        return ResponseEntity.ok(response);
    }

    /**
     * Remove a product from the cart.
     * @param productId Product ID
     * @return String
     */
    @PostMapping("/remove")
    public String removeFromCart(@RequestParam Long productId) {
        cartService.removeProduct(productId);
        return "redirect:/cart";
    }

    /**
     * Update the quantity of a product in the cart.
     * @param productId Product ID
     * @param quantity Quantity
     * @return String
     */
    @PostMapping("/update")
    public String updateCart(@RequestParam Long productId, @RequestParam int quantity) {
        cartService.updateQuantity(productId, quantity);
        return "redirect:/cart";
    }

    /**
     * View the cart.
     * @param model Model
     * @return String
     */
    @GetMapping
    public String viewCart(Model model) {
        model.addAttribute("cartEntries", cartService.getEntries());
        model.addAttribute("total", cartService.getTotal());
        model.addAttribute("isEmpty", cartService.isEmpty());
        return "cart";
    }

    /**
     * Add the cart size to the model.
     * @param model Model
     */
    @ModelAttribute
    public void addCartSizeToModel(Model model) {
        model.addAttribute("cartSize", cartService.getTotalQuantity());
    }
}