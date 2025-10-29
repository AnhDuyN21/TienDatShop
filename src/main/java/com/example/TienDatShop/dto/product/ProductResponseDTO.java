package com.example.TienDatShop.dto.product;

import com.example.TienDatShop.entity.enumeration.ProductStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponseDTO {
    private Long id;
    private Long brandId;
    private String origin;
    private String category;
    private String name;
    private String description;
    private BigDecimal price;
    private ProductStatus status;
    private String ingredients;
    private String usageInstruction;
    private BigDecimal weight;
    private String storageCondition;
    private int stockQuantity;
    private List<String> images;
}
