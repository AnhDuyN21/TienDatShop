package com.example.TienDatShop.dto.cart;

import com.example.TienDatShop.dto.cart.cartItem.CartItemRequestDTO;
import com.example.TienDatShop.entity.enumeration.CartStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartRequestDTO {
    private Long customerId;
    private String promotionCode;
    private List<CartItemRequestDTO> items;
    private CartStatus status;
}
