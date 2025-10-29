package com.example.TienDatShop.dto.order;

import com.example.TienDatShop.dto.cart.cartItem.CartItemResponseDTO;
import com.example.TienDatShop.entity.enumeration.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderResponseDTO {
    private Long id;
    private Long customerId;
    private String promotionCode;
    private LocalDateTime orderDate;
    private BigDecimal totalAmount;
    private OrderStatus status;
    private List<CartItemResponseDTO> details;
}
