package com.example.TienDatShop.service.mapper;

import com.example.TienDatShop.dto.brand.BrandRequestDTO;
import com.example.TienDatShop.dto.brand.BrandResponseDTO;
import com.example.TienDatShop.entity.Brands;
import org.springframework.stereotype.Component;

@Component
public class BrandMapper {

    // Helper để set field nếu không null
    private <T> void setIfNotNull(T value, java.util.function.Consumer<T> setter) {
        if (value != null) setter.accept(value);
    }

    // RequestDTO → Entity (tạo mới)
    public Brands toEntity(BrandRequestDTO dto) {
        if (dto == null) return null;

        return Brands.builder()
                .name(dto.getName())
                .address(dto.getAddress())
                .phone(dto.getPhone())
                .email(dto.getEmail())
                .build();
    }

    // Entity → ResponseDTO
    public BrandResponseDTO toResponseDTO(Brands brand) {
        if (brand == null) return null;

        return BrandResponseDTO.builder()
                .id(brand.getId())
                .name(brand.getName())
                .address(brand.getAddress())
                .phone(brand.getPhone())
                .email(brand.getEmail())
                .build();
    }

    // Cập nhật entity từ DTO (chỉ update field không null)
    public void updateEntityFromDTO(BrandRequestDTO dto, Brands brand) {
        if (dto == null || brand == null) return;

        setIfNotNull(dto.getName(), brand::setName);
        setIfNotNull(dto.getAddress(), brand::setAddress);
        setIfNotNull(dto.getPhone(), brand::setPhone);
        setIfNotNull(dto.getEmail(), brand::setEmail);
    }
}

