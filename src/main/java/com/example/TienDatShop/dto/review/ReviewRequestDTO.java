package com.example.TienDatShop.dto.review;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReviewRequestDTO {
    private Long productId;
    private Long customerId;
    private Long orderId;
    private String comment;
    private String status;
    private LocalDateTime commentDate;
}
