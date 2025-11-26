package com.example.TienDatShop.service.mapper;

import com.example.TienDatShop.dto.product.ProductRequestDTO;
import com.example.TienDatShop.dto.product.ProductResponseDTO;
import com.example.TienDatShop.entity.Image;
import com.example.TienDatShop.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = {BrandMapper.class, ProductDetailMapper.class})
public interface ProductMapper {
    @Mapping(source = "ingredients", target = "detail.ingredients")
    @Mapping(source = "usageInstruction", target = "detail.usageInstruction")
    @Mapping(source = "weight", target = "detail.weight")
    @Mapping(source = "storageCondition", target = "detail.storageCondition")
    @Mapping(source = "stockQuantity", target = "detail.stockQuantity")
    Product toEntity(ProductRequestDTO dto);

    @Mapping(source = "detail.ingredients", target = "ingredients")
    @Mapping(source = "detail.usageInstruction", target = "usageInstruction")
    @Mapping(source = "detail.weight", target = "weight")
    @Mapping(source = "detail.storageCondition", target = "storageCondition")
    @Mapping(source = "detail.stockQuantity", target = "stockQuantity")
    @Mapping(target = "brandName", expression = "java(getBrandName(product))")
    ProductResponseDTO toDto(Product product);

    @Mapping(source = "ingredients", target = "detail.ingredients")
    @Mapping(source = "usageInstruction", target = "detail.usageInstruction")
    @Mapping(source = "weight", target = "detail.weight")
    @Mapping(source = "storageCondition", target = "detail.storageCondition")
    @Mapping(source = "stockQuantity", target = "detail.stockQuantity")
    @Mapping(target = "brandName", expression = "java(getBrandName(product))")
    List<ProductResponseDTO> toDto(List<Product> products);

    default List<String> map(List<Image> images) {
        if (images == null) return null;

        return images.stream()
                .map(Image::getImageUrl)
                .collect(Collectors.toList());
    }

    default String getBrandName(Product product) {
        if (product.getProductBrands() != null && !product.getProductBrands().isEmpty()) {
            return product.getProductBrands().get(0).getBrand().getName();
        }
        return null;
    }

}

