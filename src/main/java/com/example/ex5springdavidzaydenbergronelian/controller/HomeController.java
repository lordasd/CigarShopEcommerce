package com.example.ex5springdavidzaydenbergronelian.controller;

import com.example.ex5springdavidzaydenbergronelian.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controller for the home page.
 */
@Controller
public class HomeController {
    private final ProductService productService;

    /**
     * Constructor for the HomeController.
     * @param productService the ProductService to use
     */
    @Autowired
    public HomeController(ProductService productService) {
        this.productService = productService;
    }

    /**
     * Displays the home page.
     * @param model the model to add attributes to
     * @return the name of the template to display
     */
    @GetMapping("/")
    public String shop(Model model) {
        model.addAttribute("products", productService.getAllProducts());
        return "index";
    }
}
