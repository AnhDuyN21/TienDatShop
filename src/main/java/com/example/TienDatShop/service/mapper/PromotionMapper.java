package com.example.TienDatShop.service.mapper;

import com.example.TienDatShop.dto.promotion.PromotionRequestDTO;
import com.example.TienDatShop.dto.promotion.PromotionResponseDTO;
import com.example.TienDatShop.entity.Promotion;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface PromotionMapper {
    Promotion toEntity(PromotionRequestDTO dto);

    PromotionResponseDTO toDto(Promotion entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updatePromotion(@MappingTarget Promotion promotion, PromotionRequestDTO dto);
}
