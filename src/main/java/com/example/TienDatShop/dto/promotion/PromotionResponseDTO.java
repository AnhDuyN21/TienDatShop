package com.example.TienDatShop.dto.promotion;

import com.example.TienDatShop.entity.enumeration.PromotionStatus;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PromotionResponseDTO {
    private Long id;
    private BigDecimal discountPercent;
    private int usageLimit;
    private LocalDateTime validFrom;
    private LocalDateTime validTo;
    private String code;
    private PromotionStatus status;
}
