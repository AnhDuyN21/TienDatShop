package com.example.TienDatShop.dto.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequestDTO {
    private Long brandId;
    private String origin;
    private String category;
    private String name;
    private String description;
    private BigDecimal price;
    private String ingredients;
    private String usageInstruction;
    private BigDecimal weight;
    private String storageCondition;
    private int stockQuantity;
    private List<String> imageUrls;
}
