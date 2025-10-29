package com.example.TienDatShop.service;

import com.example.TienDatShop.dto.image.ImageRequestDTO;
import com.example.TienDatShop.dto.image.ImageResponseDTO;

import java.util.List;

public interface ImageService {
    List<ImageResponseDTO> createImages(ImageRequestDTO dto);

    List<ImageResponseDTO> getByProduct(Long productId);
}
