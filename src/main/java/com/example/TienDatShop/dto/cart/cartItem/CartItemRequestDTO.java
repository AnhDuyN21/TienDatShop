package com.example.TienDatShop.dto.cart.cartItem;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItemRequestDTO {
    private Long productId;
    private Integer quantity;
    private BigDecimal priceAtPurchase;
}
