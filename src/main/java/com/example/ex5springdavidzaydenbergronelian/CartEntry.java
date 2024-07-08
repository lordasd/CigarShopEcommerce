package com.example.ex5springdavidzaydenbergronelian;

import com.example.ex5springdavidzaydenbergronelian.model.Product;
import lombok.Getter;

import java.math.BigDecimal;

/**
 * Represents a single entry in the shopping cart.
 */
@Getter
public class CartEntry {
    private Product product;
    private int quantity;
    private BigDecimal total;

    /**
     * Creates a new cart entry.
     *
     * @param product  the product
     * @param quantity the quantity
     */
    public CartEntry(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
        updateTotal();
    }

    /**
     * Updates the total price of the entry.
     */
    public void updateTotal() {
        this.total = product.getPrice().multiply(BigDecimal.valueOf(quantity));
    }

    /**
     * Sets the product and updates the total price.
     *
     * @param product the product
     */
    public void setProduct(Product product) {
        this.product = product;
        updateTotal();
    }

    /**
     * Sets the quantity and updates the total price.
     *
     * @param quantity the quantity
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
        updateTotal();
    }

}
