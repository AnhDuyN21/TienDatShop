package com.example.TienDatShop.service.mapper;

import com.example.TienDatShop.dto.product.ProductRequestDTO;
import com.example.TienDatShop.dto.product.ProductResponseDTO;
import com.example.TienDatShop.entity.ProductDetail;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductDetailMapper {

    ProductDetail toEntity(ProductRequestDTO dto);

    ProductResponseDTO toDto(ProductDetail detail);

}

