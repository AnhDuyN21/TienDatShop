package com.example.TienDatShop.service.mapper;

import com.example.TienDatShop.dto.cart.cartItem.CartItemRequestDTO;
import com.example.TienDatShop.dto.cart.cartItem.CartItemResponseDTO;
import com.example.TienDatShop.entity.CartItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface CartItemMapper {

    @Named("createCartItemsWithCart")
    CartItem toEntity(CartItemRequestDTO dto);

    @Mapping(source = "product.id", target = "productId")
    CartItemResponseDTO toDto(CartItem entity);
}
