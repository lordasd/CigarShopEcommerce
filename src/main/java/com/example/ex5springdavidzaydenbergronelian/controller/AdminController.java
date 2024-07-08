package com.example.ex5springdavidzaydenbergronelian.controller;

import com.example.ex5springdavidzaydenbergronelian.model.Product;
import com.example.ex5springdavidzaydenbergronelian.model.PurchaseOrder;
import com.example.ex5springdavidzaydenbergronelian.model.User;
import com.example.ex5springdavidzaydenbergronelian.service.OrderService;
import com.example.ex5springdavidzaydenbergronelian.service.ProductService;
import com.example.ex5springdavidzaydenbergronelian.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.util.List;

import static com.example.ex5springdavidzaydenbergronelian.constants.ErrorMessages.ERROR_DELETING_PRODUCT;
import static com.example.ex5springdavidzaydenbergronelian.constants.ErrorMessages.ERROR_UPDATING_PRODUCT;
import static com.example.ex5springdavidzaydenbergronelian.constants.SuccessMessages.PRODUCT_DELETED_SUCCESS;
import static com.example.ex5springdavidzaydenbergronelian.constants.SuccessMessages.PRODUCT_UPDATED_SUCCESS;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    @GetMapping
    public String adminDashboard(Model model) {
        List<PurchaseOrder> orders = orderService.findAll();
        List<User> users = userService.findAll();
        List<Product> products = productService.findAll();
        List<Product> lowStockProducts = productService.findLowStockProducts(10);
        BigDecimal totalSales = orderService.calculateTotalSales();
        long totalOrders = orderService.countTotalOrders();

        model.addAttribute("orders", orders);
        model.addAttribute("users", users);
        model.addAttribute("products", products);
        model.addAttribute("lowStockProducts", lowStockProducts);
        model.addAttribute("totalSales", totalSales);
        model.addAttribute("totalOrders", totalOrders);

        return "admin";
    }

    @GetMapping("/user/{userId}")
    public String viewUserProfile(@PathVariable Long userId, Model model) {
        User user = userService.findById(userId);
        List<PurchaseOrder> orders = orderService.findByUser(user);
        model.addAttribute("user", user);
        model.addAttribute("orders", orders);
        return "user-profile";
    }

    @PostMapping("/order/{orderId}/status")
    public String updateOrderStatus(@PathVariable Long orderId, @RequestParam String status) {
        orderService.updateOrderStatus(orderId, status);
        return "redirect:/admin";
    }

    @PostMapping("/user/{userId}/order/{orderId}/status")
    public String updateOrderStatusFromUserProfile(@PathVariable Long userId, @PathVariable Long orderId, @RequestParam String status) {
        orderService.updateOrderStatus(orderId, status);
        return "redirect:/admin/user/" + userId;
    }

    @GetMapping("/product/add")
    public String addProductForm(Model model) {
        model.addAttribute("product", new Product());
        return "add-product";
    }

    @PostMapping("/product/add")
    public String addProduct(@ModelAttribute Product product) {
        productService.save(product);
        return "redirect:/admin";
    }

    @GetMapping("/product/edit/{productId}")
    public String editProductForm(@PathVariable Long productId, Model model) {
        Product product = productService.findById(productId);
        model.addAttribute("product", product);
        return "edit-product";
    }

    @PostMapping("/product/edit")
    public String editProduct(@ModelAttribute Product product, RedirectAttributes redirectAttributes) {
        try {
            productService.saveOrUpdate(product);
            redirectAttributes.addFlashAttribute("successMessage", PRODUCT_UPDATED_SUCCESS);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", ERROR_UPDATING_PRODUCT + e.getMessage());
        }
        return "redirect:/admin";
    }

    @PostMapping("/product/delete/{productId}")
    public String deleteProduct(@PathVariable Long productId, RedirectAttributes redirectAttributes) {
        try {
            productService.deleteById(productId);
            redirectAttributes.addFlashAttribute("notificationMessage", PRODUCT_DELETED_SUCCESS);
            redirectAttributes.addFlashAttribute("notificationType", "success");
        } catch (DataIntegrityViolationException e) {
            redirectAttributes.addFlashAttribute("notificationMessage", ERROR_DELETING_PRODUCT);
            redirectAttributes.addFlashAttribute("notificationType", "error");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("notificationMessage", ERROR_DELETING_PRODUCT + e.getMessage());
            redirectAttributes.addFlashAttribute("notificationType", "error");
        }
        return "redirect:/admin";
    }
}
