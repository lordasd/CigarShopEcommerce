package com.example.ex5springdavidzaydenbergronelian.service;

import com.example.ex5springdavidzaydenbergronelian.CartEntry;
import com.example.ex5springdavidzaydenbergronelian.model.Product;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import jakarta.annotation.PostConstruct;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * Service class for managing the shopping cart.
 */
@Getter
@Service
@SessionScope
public class CartService {
    private Map<Long, CartEntry> entries;
    @Getter
    private boolean orderPlaced = false;

    /**
     * Initializes the shopping cart.
     */
    @PostConstruct
    public void init() {
        entries = new HashMap<>();
    }

    /**
     * Adds a product to the shopping cart.
     * @param product  the product to add
     * @param quantity the quantity to add
     */
    public void addProduct(Product product, int quantity) {
        CartEntry entry = entries.get(product.getId());
        if (entry == null) {
            entry = new CartEntry(product, quantity);
            entries.put(product.getId(), entry);
        } else
            entry.setQuantity(entry.getQuantity() + quantity);
    }

    /**
     * Removes a product from the shopping cart.
     * @param productId the ID of the product to remove
     */
    public void removeProduct(Long productId) {
        entries.remove(productId);
    }

    /**
     * Updates the quantity of a product in the shopping cart.
     * @param productId the ID of the product to update
     * @param quantity  the new quantity
     */
    public void updateQuantity(Long productId, int quantity) {
        CartEntry entry = entries.get(productId);
        if (entry != null) {
            entry.setQuantity(quantity);
            entry.updateTotal();
        }
    }

    /**
     * Returns the total price of the shopping cart.
     * @return the total price
     */
    public BigDecimal getTotal() {
        return entries.values().stream()
                .map(CartEntry::getTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    /**
     * Returns the total quantity of products in the shopping cart.
     * @return the total quantity
     */
    public int getTotalQuantity() {
        return entries.values().stream()
                .mapToInt(CartEntry::getQuantity)
                .sum();
    }

    /**
     * Returns whether the shopping cart is empty.
     * @return true if the shopping cart is empty, false otherwise
     */
    public boolean isEmpty() {
        return entries.isEmpty();
    }

    /**
     * Clears the shopping cart.
     */
    public void clear() {
        entries.clear();
        orderPlaced = true;
    }

    /**
     * Resets the order placed flag.
     */
    public void resetOrderPlaced() {
        orderPlaced = false;
    }
}
