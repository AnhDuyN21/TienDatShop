package com.example.TienDatShop.service.mapper;

import com.example.TienDatShop.dto.promotion.PromotionRequestDTO;
import com.example.TienDatShop.dto.promotion.PromotionResponseDTO;
import com.example.TienDatShop.entity.Promotions;
import org.springframework.stereotype.Component;

@Component
public class PromotionMapper {

    // Helper để set field nếu không null
    private <T> void setIfNotNull(T value, java.util.function.Consumer<T> setter) {
        if (value != null) setter.accept(value);
    }

    // RequestDTO → Entity (tạo mới)
    public Promotions toEntity(PromotionRequestDTO dto) {
        if (dto == null) return null;

        return Promotions.builder()
                .code(dto.getCode())
                .discountPercent(dto.getDiscountPercent())
                .usageLimit(dto.getUsageLimit())
                .validFrom(dto.getValidFrom())
                .validTo(dto.getValidTo())
                .build();
    }

    // Entity → ResponseDTO
    public PromotionResponseDTO toDto(Promotions entity) {
        if (entity == null) return null;

        return PromotionResponseDTO.builder()
                .id(entity.getId())
                .code(entity.getCode())
                .discountPercent(entity.getDiscountPercent())
                .usageLimit(entity.getUsageLimit())
                .validFrom(entity.getValidFrom())
                .validTo(entity.getValidTo())
                .build();
    }

    // Update entity từ DTO (chỉ update field không null)
    public void updateEntityFromDto(PromotionRequestDTO dto, Promotions entity) {
        if (dto == null || entity == null) return;

        setIfNotNull(dto.getCode(), entity::setCode);
        setIfNotNull(dto.getDiscountPercent(), entity::setDiscountPercent);
        setIfNotNull(dto.getUsageLimit(), entity::setUsageLimit);
        setIfNotNull(dto.getValidFrom(), entity::setValidFrom);
        setIfNotNull(dto.getValidTo(), entity::setValidTo);
    }
}
