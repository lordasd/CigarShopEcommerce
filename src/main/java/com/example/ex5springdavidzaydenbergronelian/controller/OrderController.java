package com.example.ex5springdavidzaydenbergronelian.controller;

import com.example.ex5springdavidzaydenbergronelian.model.PurchaseOrder;
import com.example.ex5springdavidzaydenbergronelian.model.User;
import com.example.ex5springdavidzaydenbergronelian.service.OrderService;
import com.example.ex5springdavidzaydenbergronelian.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.math.BigDecimal;
import java.security.Principal;

/**
 * Controller for handling order-related requests.
 */
@Controller
public class OrderController {
    private final UserService userService;
    private final OrderService orderService;

    /**
     * Constructor for the OrderController.
     * @param userService the UserService to use
     * @param orderService the OrderService to use
     */
    @Autowired
    public OrderController(UserService userService, OrderService orderService) {
        this.userService = userService;
        this.orderService = orderService;
    }

    /**
     * Displays the user's orders.
     * @param model the model to add attributes to
     * @param principal the currently logged-in user
     * @return the name of the template to display
     */
    @GetMapping("/account/orders/{orderId}")
    public String showOrderDetails(@PathVariable Long orderId, Model model, Principal principal) {
        User user = userService.findByUsername(principal.getName());
        PurchaseOrder order = orderService.findById(orderId);
        if (order == null || !order.getUser().equals(user))
            return "redirect:/account"; // Order not found or does not belong to the user

        BigDecimal totalPrice = order.getProducts().stream()
                .map(product -> product.getPrice().multiply(new BigDecimal(product.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        model.addAttribute("order", order);
        model.addAttribute("totalPrice", totalPrice);
        return "order-details";
    }
}
