package com.example.TienDatShop.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "ProductDetails")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id", nullable = false)
    private Products productId;

    @Column(nullable = false)
    private String ingredients;

    @Column(nullable = false, name = "usage_instructions")
    private String usageInstruction;

    @Column(nullable = false, precision = 10, scale = 3)
    private BigDecimal weight;

    @Column(nullable = false, length = 50,name = "storage_conditions")
    private String storageCondition;

    @Column(nullable = false, name = "stock_quantity")
    private int stockQuantity;
}
