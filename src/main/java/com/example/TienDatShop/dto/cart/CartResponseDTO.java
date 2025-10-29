package com.example.TienDatShop.dto.cart;

import com.example.TienDatShop.dto.cart.cartItem.CartItemResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartResponseDTO {
    private Long id;
    private Long customerId;
    private BigDecimal totalAmount;
    private String promotionCode;
    private List<CartItemResponseDTO> items;
}
