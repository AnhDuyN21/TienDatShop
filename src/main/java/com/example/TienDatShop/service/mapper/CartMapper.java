package com.example.TienDatShop.service.mapper;

import com.example.TienDatShop.dto.cart.CartRequestDTO;
import com.example.TienDatShop.dto.cart.CartResponseDTO;
import com.example.TienDatShop.entity.Cart;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = {CartItemMapper.class})
public interface CartMapper {
    @Mapping(source = "items", target = "items", qualifiedByName = "createCartItemsWithCart")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Cart toEntity(CartRequestDTO dto);

    @Mapping(source = "customer.id", target = "customerId")
    CartResponseDTO toDto(Cart entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateCart(@MappingTarget Cart existing, CartRequestDTO dto);
}
