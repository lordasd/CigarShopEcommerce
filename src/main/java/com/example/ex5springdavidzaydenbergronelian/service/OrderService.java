package com.example.ex5springdavidzaydenbergronelian.service;

import com.example.ex5springdavidzaydenbergronelian.CartEntry;
import com.example.ex5springdavidzaydenbergronelian.model.OrderProduct;
import com.example.ex5springdavidzaydenbergronelian.model.Product;
import com.example.ex5springdavidzaydenbergronelian.model.PurchaseOrder;
import com.example.ex5springdavidzaydenbergronelian.model.User;
import com.example.ex5springdavidzaydenbergronelian.repository.OrderProductRepository;
import com.example.ex5springdavidzaydenbergronelian.repository.ProductRepository;
import com.example.ex5springdavidzaydenbergronelian.repository.PurchaseOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.example.ex5springdavidzaydenbergronelian.constants.ErrorMessages.INSUFFICIENT_STOCK;
import static com.example.ex5springdavidzaydenbergronelian.constants.ErrorMessages.INVALID_ORDER_ID;

/**
 * Service class for managing purchase orders.
 */
@Service
public class OrderService {

    @Autowired
    private PurchaseOrderRepository purchaseOrderRepository;

    @Autowired
    private OrderProductRepository orderProductRepository;

    @Autowired
    private ProductRepository productRepository;

    /**
     * Creates a new purchase order.
     *
     * @param user          the user who placed the order
     * @param entries       the products and quantities in the order
     * @param name          the name of the recipient
     * @param address       the address of the recipient
     * @param paymentMethod the payment method
     * @return the created purchase order
     */
    @Transactional
    public PurchaseOrder createOrder(User user, Map<Long, CartEntry> entries, String name, String address, String paymentMethod) {
        List<String> errorMessages = new ArrayList<>();

        for (CartEntry entry : entries.values()) {
            if (entry.getQuantity() > entry.getProduct().getStockQuantity())
                errorMessages.add(String.format(INSUFFICIENT_STOCK,
                        entry.getProduct().getName(), entry.getProduct().getStockQuantity(), entry.getQuantity()));
        }

        if (!errorMessages.isEmpty())
            throw new IllegalArgumentException(String.join("|", errorMessages));

        PurchaseOrder order = new PurchaseOrder();
        order.setUser(user);
        order.setOrderDate(LocalDateTime.now());
        order.setStatus("PENDING");
        order.setName(name);
        order.setAddress(address);
        order.setPaymentMethod(paymentMethod.equals("creditCard") ? "Credit Card" : paymentMethod.equals("paypal") ? "PayPal" : paymentMethod);

        order = purchaseOrderRepository.save(order);

        for (CartEntry cartEntry : entries.values()) {
            OrderProduct orderProduct = new OrderProduct();
            orderProduct.setProduct(cartEntry.getProduct());
            orderProduct.setQuantity(cartEntry.getQuantity());
            orderProduct.setPrice(cartEntry.getProduct().getPrice());
            orderProduct.setPurchaseOrder(order);

            orderProductRepository.save(orderProduct);

            order.getProducts().add(orderProduct);

            // Reduce stock quantity and save the product
            Product product = cartEntry.getProduct();
            product.setStockQuantity(product.getStockQuantity() - cartEntry.getQuantity());
            productRepository.save(product);
        }

        return order;
    }

    /**
     * Updates the status of a purchase order.
     *
     * @param orderId the ID of the order
     * @param status  the new status
     */
    @Transactional
    public void updateOrderStatus(Long orderId, String status) {
        PurchaseOrder order = purchaseOrderRepository.findById(orderId).orElseThrow(() -> new IllegalArgumentException(INVALID_ORDER_ID + orderId));
        order.setStatus(status);
        purchaseOrderRepository.save(order);
    }

    /**
     * Finds a purchase order by ID.
     *
     * @param id the ID of the order
     * @return the purchase order, or null if not found
     */
    public PurchaseOrder findById(Long id) {
        return purchaseOrderRepository.findById(id).orElse(null);
    }

    /**
     * Finds all purchase orders for a user.
     *
     * @param user the user
     * @return the list of purchase orders
     */
    public List<PurchaseOrder> findByUser(User user) {
        return purchaseOrderRepository.findByUser(user);
    }

    /**
     * Finds all purchase orders.
     *
     * @return the list of purchase orders
     */
    public List<PurchaseOrder> findAll() {
        return purchaseOrderRepository.findAll();
    }

    /**
     * Calculates the total sales amount.
     *
     * @return the total sales amount
     */
    public BigDecimal calculateTotalSales() {
        return purchaseOrderRepository.findAll().stream()
                .map(PurchaseOrder::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    /**
     * Counts the total number of orders.
     *
     * @return the total number of orders
     */
    public long countTotalOrders() {
        return purchaseOrderRepository.count();
    }
}
