package com.example.TienDatShop.service;

import com.example.TienDatShop.dto.product.ProductRequestDTO;
import com.example.TienDatShop.dto.product.ProductResponseDTO;

import java.util.List;

public interface ProductService {
    ProductResponseDTO create(ProductRequestDTO dto);

    List<ProductResponseDTO> getAll();

    ProductResponseDTO getById(Long id);

//    ProductResponseDTO update(ProductRequestDTO dto, Long productId);

}
