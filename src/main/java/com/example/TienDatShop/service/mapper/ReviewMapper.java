package com.example.TienDatShop.service.mapper;

import com.example.TienDatShop.dto.review.ReviewRequestDTO;
import com.example.TienDatShop.dto.review.ReviewResponseDTO;
import com.example.TienDatShop.entity.Review;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface ReviewMapper {
    @Mapping(source = "productId", target = "product.id")
    @Mapping(source = "customerId", target = "customer.id")
    @Mapping(source = "orderId", target = "order.id")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Review toEntity(ReviewRequestDTO dto);

    @Mapping(source = "product.id", target = "productId")
    @Mapping(source = "customer.id", target = "customerId")
    @Mapping(source = "order.id", target = "orderId")
    ReviewResponseDTO toDto(Review entity);
}
