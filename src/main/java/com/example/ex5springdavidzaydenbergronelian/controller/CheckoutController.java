package com.example.ex5springdavidzaydenbergronelian.controller;

import com.example.ex5springdavidzaydenbergronelian.model.PurchaseOrder;
import com.example.ex5springdavidzaydenbergronelian.model.User;
import com.example.ex5springdavidzaydenbergronelian.service.CartService;
import com.example.ex5springdavidzaydenbergronelian.service.OrderService;
import com.example.ex5springdavidzaydenbergronelian.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Controller for handling checkout process
 */
@Controller
@RequestMapping("/checkout")
@SessionAttributes("order")
public class CheckoutController {
    private final CartService cartService;
    private final OrderService orderService;
    private final UserService userService;

    /**
     * Constructor for CheckoutController
     * @param cartService CartService
     * @param orderService OrderService
     * @param userService UserService
     */
    @Autowired
    public CheckoutController(CartService cartService, OrderService orderService, UserService userService) {
        this.cartService = cartService;
        this.orderService = orderService;
        this.userService = userService;
    }

    /**
     * Show the checkout page
     * @param userDetails UserDetails
     * @param model Model
     * @return checkout HTML page
     */
    @GetMapping
    public String showCheckoutPage(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        if (cartService.isEmpty())
            return "redirect:/cart";

        User user = userService.findByUsername(userDetails.getUsername());
        model.addAttribute("user", user);
        model.addAttribute("cartEntries", cartService.getEntries());
        model.addAttribute("total", cartService.getTotal());

        return "checkout"; // Return the checkout HTML page
    }

    /**
     * Handle the checkout process
     * @param userDetails UserDetails
     * @param name Name
     * @param address Address
     * @param paymentMethod Payment method
     * @param redirectAttributes RedirectAttributes
     * @param model Model
     * @return checkout success page
     */
    @PostMapping("/confirm")
    public String checkout(@AuthenticationPrincipal UserDetails userDetails,
                           @RequestParam String name,
                           @RequestParam String address,
                           @RequestParam String paymentMethod,
                           RedirectAttributes redirectAttributes,
                           Model model) {
        if (cartService.isEmpty())
            return "redirect:/cart";

        User user = userService.findByUsername(userDetails.getUsername());

        try {
            PurchaseOrder order = orderService.createOrder(user, cartService.getEntries(), name, address, paymentMethod);
            model.addAttribute("order", order); // Add order to session attributes
            cartService.clear();
            return "redirect:/checkout/success"; // Redirect to success page
        } catch (IllegalArgumentException e) {
            String[] errorMessages = e.getMessage().split("\\|");
            model.addAttribute("errors", errorMessages);
            model.addAttribute("user", user);
            model.addAttribute("cartEntries", cartService.getEntries());
            model.addAttribute("total", cartService.getTotal());
            return "checkout"; // Return to checkout page with error
        }
    }

    /**
     * Show the success page
     * @param order PurchaseOrder
     * @param model Model
     * @return checkout success HTML page
     */
    @GetMapping("/success")
    public String showSuccessPage(@ModelAttribute("order") PurchaseOrder order, Model model) {
        if (order == null || !cartService.isOrderPlaced())
            return "redirect:/";

        cartService.resetOrderPlaced(); // Reset the flag after showing the success page
        model.addAttribute("order", order);
        return "checkout-success";
    }

}
