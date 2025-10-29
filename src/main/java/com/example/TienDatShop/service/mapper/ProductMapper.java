package com.example.TienDatShop.service.mapper;

import com.example.TienDatShop.dto.product.ProductRequestDTO;
import com.example.TienDatShop.dto.product.ProductResponseDTO;
import com.example.TienDatShop.entity.Images;
import com.example.TienDatShop.entity.ProductDetails;
import com.example.TienDatShop.entity.Products;
import com.example.TienDatShop.entity.enumeration.ProductStatus;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class ProductMapper {

    // Helper để set field nếu không null
    private <T> void setIfNotNull(T value, java.util.function.Consumer<T> setter) {
        if (value != null) setter.accept(value);
    }

    // RequestDTO -> Products entity (không set brand, images)
    public Products toEntity(ProductRequestDTO dto) {
        if (dto == null) return null;

        Products product = new Products();
        product.setOrigin(dto.getOrigin());
        product.setCategory(dto.getCategory());
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setStatus(ProductStatus.AVAILABLE); // default

        return product;
    }

    // ProductDetails entity từ DTO
    public ProductDetails toDetailsEntity(ProductRequestDTO dto, Products product) {
        if (dto == null || product == null) return null;

        ProductDetails details = new ProductDetails();
        details.setProductId(product);
        details.setIngredients(dto.getIngredients());
        details.setUsageInstruction(dto.getUsageInstructions());
        details.setWeight(dto.getWeight());
        details.setStorageCondition(dto.getStorageCondition());
        details.setStockQuantity(dto.getStockQuantity());

        return details;
    }

    // Entity + Details -> ResponseDTO
    public ProductResponseDTO toDto(Products product, ProductDetails details) {
        if (product == null) return null;

        List<String> images = mapImagesToUrls(product.getImages());

        return ProductResponseDTO.builder()
                .id(product.getId())
                .brandId(product.getBrandId() != null ? product.getBrandId().getId() : null)
                .origin(product.getOrigin())
                .category(product.getCategory())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .status(product.getStatus())
                .ingredients(details != null ? details.getIngredients() : null)
                .usageInstruction(details != null ? details.getUsageInstruction() : null)
                .weight(details != null ? details.getWeight() : null)
                .storageCondition(details != null ? details.getStorageCondition() : null)
                .stockQuantity(details != null ? details.getStockQuantity() : 0)
                .images(images)
                .build();
    }

    // Cập nhật Products từ DTO (chỉ field không null)
    public void updateProductFromDTO(ProductRequestDTO dto, Products product) {
        if (dto == null || product == null) return;

        setIfNotNull(dto.getOrigin(), product::setOrigin);
        setIfNotNull(dto.getCategory(), product::setCategory);
        setIfNotNull(dto.getName(), product::setName);
        setIfNotNull(dto.getDescription(), product::setDescription);
        setIfNotNull(dto.getPrice(), product::setPrice);
        // status không update ở đây
    }

    // Cập nhật ProductDetails từ DTO (chỉ field không null)
    public void updateDetailsFromDTO(ProductRequestDTO dto, ProductDetails details) {
        if (dto == null || details == null) return;

        setIfNotNull(dto.getIngredients(), details::setIngredients);
        setIfNotNull(dto.getUsageInstructions(), details::setUsageInstruction);
        setIfNotNull(dto.getWeight(), details::setWeight);
        setIfNotNull(dto.getStorageCondition(), details::setStorageCondition);
        // stockQuantity nếu muốn update
        details.setStockQuantity(dto.getStockQuantity());
    }

    // Chuyển List<Images> -> List<String>
    private List<String> mapImagesToUrls(List<Images> images) {
        if (images == null) return Collections.emptyList();
        List<String> urls = new ArrayList<>();
        for (Images img : images) {
            if (img != null && img.getImageUrl() != null) {
                urls.add(img.getImageUrl());
            }
        }
        return urls;
    }
}
