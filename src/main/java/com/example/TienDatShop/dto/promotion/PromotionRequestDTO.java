package com.example.TienDatShop.dto.promotion;

import jakarta.validation.constraints.*;
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
    @NotNull(message = "Discount percent is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Discount must be greater than 0")
    @DecimalMax(value = "100.0", message = "Discount must be less than or equal to 100")
    private BigDecimal discountPercent;

    @Min(value = 1, message = "Usage limit must be at least 1")
    private int usageLimit;

    @NotNull(message = "Valid from date is required")
    private LocalDateTime validFrom;

    @NotNull(message = "Valid to date is required")
    private LocalDateTime validTo;

    @NotBlank(message = "Promotion code is required")
    private String code;
}
