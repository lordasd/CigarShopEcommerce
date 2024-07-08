package com.example.ex5springdavidzaydenbergronelian.controller;

import com.example.ex5springdavidzaydenbergronelian.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

/**
 * Global controller advice to add the cart size to all models.
 */
@ControllerAdvice
public class GlobalControllerAdvice {
    private final CartService cartService;

    /**
     * Constructor for the global controller advice.
     * @param cartService The cart service to get the cart size from.
     */
    @Autowired
    public GlobalControllerAdvice(CartService cartService) {
        this.cartService = cartService;
    }

    /**
     * Add the cart size to all models.
     * @return The cart size.
     */
    @ModelAttribute("cartSize")
    public int getCartSize() {
        return cartService.getTotalQuantity();
    }
}
