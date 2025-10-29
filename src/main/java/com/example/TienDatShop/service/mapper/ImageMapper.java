package com.example.TienDatShop.service.mapper;

import com.example.TienDatShop.dto.image.ImageResponseDTO;
import com.example.TienDatShop.entity.Images;
import org.springframework.stereotype.Component;

@Component
public class ImageMapper {

    // Entity -> ResponseDTO
    public ImageResponseDTO toDTO(Images entity) {
        if (entity == null) return null;

        return ImageResponseDTO.builder()
                .id(entity.getId())
                .productId(entity.getProductId() != null ? entity.getProductId().getId() : null)
                .imageUrl(entity.getImageUrl())
                .build();
    }
}
