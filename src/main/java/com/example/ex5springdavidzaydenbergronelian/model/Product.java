package com.example.ex5springdavidzaydenbergronelian.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static com.example.ex5springdavidzaydenbergronelian.constants.InfoMessages.*;

/**
 * Product entity.
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@Setter
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = PRODUCT_ID_MANDATORY)
    @Column(nullable = false, unique = true)
    private String productId;

    @NotBlank(message = NAME_MANDATORY)
    @Column(nullable = false)
    private String name;

    @NotBlank(message = CATEGORY_MANDATORY)
    @Column(nullable = false)
    private String category;

    @NotBlank(message = BRAND_MANDATORY)
    @Column(nullable = false)
    private String brand;

    private String description;

    @NotNull(message = PRICE_MANDATORY)
    @Column(nullable = false)
    private BigDecimal price;

    @NotNull(message = STOCK_QUANTITY_MANDATORY)
    @Column(nullable = false)
    private int stockQuantity;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviews = new ArrayList<>();
}
