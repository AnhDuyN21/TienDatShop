package com.example.TienDatShop.dto.promotion;

import com.example.TienDatShop.entity.enumeration.PromotionStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PromotionRequestDTO {
    private BigDecimal discountPercent;
    private Integer usageLimit;
    private LocalDateTime validFrom;
    private LocalDateTime validTo;
    private String code;
    private PromotionStatus status;
}
