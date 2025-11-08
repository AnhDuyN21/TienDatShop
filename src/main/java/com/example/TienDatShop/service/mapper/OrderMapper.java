package com.example.TienDatShop.service.mapper;

import com.example.TienDatShop.dto.order.OrderRequestDTO;
import com.example.TienDatShop.dto.order.OrderResponseDTO;
import com.example.TienDatShop.entity.Cart;
import com.example.TienDatShop.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    @Mapping(source = "cartId", target = "cart.id")
    Order toEntity(OrderRequestDTO dto);

    @Mapping(source = "customer.id", target = "customerId")
    @Mapping(source = "cart.items", target = "details")
    OrderResponseDTO toDto(Order entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "customer", source = "customer")
    @Mapping(target = "promotionCode", source = "promotionCode")
    @Mapping(target = "totalAmount", source = "totalAmount")
    @Mapping(target = "status", constant = "CREATED")
    @Mapping(target = "cart", source = "cart")
    Order mapCartToOrder(Cart cart);
}
