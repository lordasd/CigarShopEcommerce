package com.example.ex5springdavidzaydenbergronelian.controller;

import com.example.ex5springdavidzaydenbergronelian.model.PurchaseOrder;
import com.example.ex5springdavidzaydenbergronelian.model.Review;
import com.example.ex5springdavidzaydenbergronelian.model.User;
import com.example.ex5springdavidzaydenbergronelian.service.OrderService;
import com.example.ex5springdavidzaydenbergronelian.service.ReviewService;
import com.example.ex5springdavidzaydenbergronelian.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.regex.Pattern;

import static com.example.ex5springdavidzaydenbergronelian.constants.ErrorMessages.*;
import static com.example.ex5springdavidzaydenbergronelian.constants.SuccessMessages.*;

/**
 * Controller for user-related pages.
 */
@Controller
public class UserController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final OrderService orderService;
    private final ReviewService reviewService;

    /**
     * Constructor for UserController.
     * @param userService User service
     * @param passwordEncoder Password encoder
     * @param orderService Order service
     * @param reviewService Review service
     */
    @Autowired
    public UserController(UserService userService, PasswordEncoder passwordEncoder, OrderService orderService, ReviewService reviewService) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.orderService = orderService;
        this.reviewService = reviewService;
    }

    /**
     * Shows the registration form.
     * @param email Email
     * @param model Model
     * @return Registration form
     */
    @GetMapping("/register")
    public String showRegistrationForm(@RequestParam(required = false) String email, Model model) {
        User user = new User();
        user.setEmail(email);
        model.addAttribute("user", user);
        return "register";
    }

    /**
     * Registers a user.
     * @param user User
     * @param result Binding result
     * @param model Model
     * @return Registration form
     */
    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute User user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("submitted", true);
            return "register";
        }
        if (userService.findByUsername(user.getUsername()) != null) {
            model.addAttribute("error", USERNAME_ALREADY_EXISTS);
            return "register";
        }
        if (userService.findByEmail(user.getEmail()) != null) {
            model.addAttribute("error", EMAIL_ALREADY_EXISTS);
            return "register";
        }
        user.setRole("ROLE_USER");
        userService.save(user);
        model.addAttribute("successMessage", REGISTRATION_SUCCESS);
        return "register";
    }

    /**
     * Shows the login page.
     * @return Login page
     */
    @GetMapping("/account")
    public String showAccountPage(Model model, Principal principal) {
        User user = userService.findByUsername(principal.getName());
        List<PurchaseOrder> orders = orderService.findByUser(user);
        List<Review> reviews = reviewService.findByUser(user);
        model.addAttribute("user", user);
        model.addAttribute("orders", orders);
        model.addAttribute("reviews", reviews);
        return "account";
    }

    /**
     * Changes the password.
     * @param currentPassword Current password
     * @param newPassword New password
     * @param confirmPassword Confirmation password
     * @param principal Principal
     * @param model Model
     * @return Account page
     */
    @PostMapping("/account/changePassword")
    public String changePassword(@RequestParam("currentPassword") String currentPassword,
                                 @RequestParam("newPassword") String newPassword,
                                 @RequestParam("confirmPassword") String confirmPassword,
                                 Principal principal,
                                 Model model) {
        User user = userService.findByUsername(principal.getName());

        // Add regex pattern check
        Pattern pattern = Pattern.compile("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,}$");
        if (!pattern.matcher(newPassword).matches()) {
            model.addAttribute("error", PASSWORD_NOT_STRONG);
        } else if (!newPassword.equals(confirmPassword)) {
            model.addAttribute("error", NEW_PASSWORD_AND_CONFIRMATION_PASSWORD_DO_NOT_MATCH);
        } else if (passwordEncoder.matches(currentPassword, user.getPassword())) {
            userService.updatePassword(user, newPassword);
            model.addAttribute("message", PASSWORD_CHANGED_SUCCESS);
        } else {
            model.addAttribute("error", CURRENT_PASSWORD_INCORRECT);
        }

        model.addAttribute("user", user);
        return "account";
    }
}
