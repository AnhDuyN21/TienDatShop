package com.example.TienDatShop.service.mapper;

import com.example.TienDatShop.dto.cart.cartItem.CartItemResponseDTO;
import com.example.TienDatShop.dto.order.OrderResponseDTO;
import com.example.TienDatShop.entity.CartItems;
import com.example.TienDatShop.entity.Carts;
import com.example.TienDatShop.entity.Customers;
import com.example.TienDatShop.entity.Orders;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class OrderMapper {

    // Orders entity -> OrderResponseDTO
    public OrderResponseDTO toDto(Orders order) {
        if (order == null) return null;

        List<CartItemResponseDTO> details = mapCartToDetails(order.getCartId());

        return OrderResponseDTO.builder()
                .id(order.getId())
                .customerId(order.getCustomerId() != null ? order.getCustomerId().getId() : null)
                .promotionCode(order.getPromotionCode())
                .orderDate(order.getOrderDate())
                .totalAmount(order.getTotalAmount())
                .status(order.getStatus())
                .details(details)
                .build();
    }

    // Carts -> List<CartItemResponseDTO>
    private List<CartItemResponseDTO> mapCartToDetails(Carts cart) {
        List<CartItemResponseDTO> list = new ArrayList<>();
        if (cart == null || cart.getItems() == null) return list;

        for (CartItems item : cart.getItems()) {
            list.add(mapCartItemToDto(item));
        }
        return list;
    }

    // Convert từng CartItem -> CartItemResponseDTO
    private CartItemResponseDTO mapCartItemToDto(CartItems item) {
        if (item == null) return null;

        CartItemResponseDTO dto = new CartItemResponseDTO();
        dto.setProductId(item.getProductId() != null ? item.getProductId().getId() : null);
        dto.setQuantity(item.getQuantity());
        dto.setPriceAtPurchase(item.getPriceAtPurchase());

        return dto;
    }

    // Customers -> Long
    private Long mapCustomerToLong(Customers customer) {
        return customer != null ? customer.getId() : null;
    }
}
