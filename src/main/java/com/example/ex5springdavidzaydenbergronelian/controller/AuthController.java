package com.example.ex5springdavidzaydenbergronelian.controller;

import com.example.ex5springdavidzaydenbergronelian.service.EmailService;
import com.example.ex5springdavidzaydenbergronelian.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

import static com.example.ex5springdavidzaydenbergronelian.constants.ErrorMessages.*;
import static com.example.ex5springdavidzaydenbergronelian.constants.InfoMessages.LOGGED_OUT_SUCCESSFULLY;
import static com.example.ex5springdavidzaydenbergronelian.constants.InfoMessages.PASSWORD_RESET_EMAIL_SENT;
import static com.example.ex5springdavidzaydenbergronelian.constants.SuccessMessages.PASSWORD_RESET_SUCCESS;

/**
 * Controller for handling user authentication and password reset.
 */
@Controller
public class AuthController {

    private final UserService userService;
    private final EmailService emailService;

    /**
     * Constructor for AuthController.
     * @param userService UserService
     * @param emailService EmailService
     */
    @Autowired
    public AuthController(UserService userService, EmailService emailService) {
        this.userService = userService;
        this.emailService = emailService;
    }

    /**
     * Display the login page.
     * @param error error
     * @param logout logout
     * @param model model
     * @return login page
     */
    @GetMapping("/login")
    public String login(@RequestParam(value = "error", required = false) String error,
                        @RequestParam(value = "logout", required = false) String logout,
                        Model model) {
        if (error != null)
            model.addAttribute("error", INVALID_CREDENTIALS);
        if (logout != null)
            model.addAttribute("message", LOGGED_OUT_SUCCESSFULLY);
        return "login";
    }

    /**
     * Display the registration page.
     * @return registration page
     */
    @GetMapping("/forgot-password")
    public String showForgotPasswordForm() {
        return "forgot-password";
    }

    /**
     * baseUrl for the application.
     */
    @Value("${app.baseUrl}")
    private String baseUrl;

    /**
     * Process the forgot password form.
     * @param email email
     * @param model model
     * @return forgot password page
     */
    @PostMapping("/forgot-password")
    public String processForgotPassword(@RequestParam("email") String email, Model model) {
        String token = UUID.randomUUID().toString();
        boolean result = userService.createPasswordResetTokenForEmail(email, token);

        if (result) {
            String resetLink = baseUrl + "/reset-password?token=" + token;
            emailService.sendPasswordResetEmail(email, resetLink);
            model.addAttribute("message", PASSWORD_RESET_EMAIL_SENT);
        } else
            model.addAttribute("error", EMAIL_NOT_FOUND);

        return "forgot-password";
    }

    /**
     * Display the reset password form.
     * @param token token
     * @param model model
     * @return reset password page
     */
    @GetMapping("/reset-password")
    public String showResetPasswordForm(@RequestParam("token") String token, Model model) {
        // Validate token (implement this method in UserService)
        boolean isValidToken = userService.validatePasswordResetToken(token);

        if (isValidToken) {
            model.addAttribute("token", token);
            return "reset-password";
        } else {
            model.addAttribute("error", INVALID_OR_EXPIRED_PASSWORD_RESET_LINK);
            return "error";
        }
    }

    /**
     * Process the reset password form.
     * @param token token
     * @param password password
     * @param confirmPassword confirmPassword
     * @param model model
     * @return reset password page
     */
    @PostMapping("/reset-password")
    public String processResetPassword(@RequestParam("token") String token,
                                       @RequestParam("password") String password,
                                       @RequestParam("confirmPassword") String confirmPassword,
                                       Model model) {
        if (!password.equals(confirmPassword)) {
            model.addAttribute("error", PASSWORDS_DONT_MATCH);
            return "reset-password";
        }

        if (!isPasswordStrong(password)) {
            model.addAttribute("error", PASSWORD_NOT_STRONG);
            return "reset-password";
        }

        // Reset password (implement this method in UserService)
        boolean result = userService.resetPassword(token, password);

        if (result) {
            model.addAttribute("message", PASSWORD_RESET_SUCCESS);
            return "login";
        } else {
            model.addAttribute("error", ERROR_RESETTING_PASSWORD);
            return "reset-password";
        }
    }

    /**
     * Check if the password is strong.
     * @param password password
     * @return true if the password is strong, false otherwise
     */
    private boolean isPasswordStrong(String password) {
        String regex = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,}$";
        return password.matches(regex);
    }
}
