package com.example.TienDatShop.dto.review;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReviewResponseDTO {
    private Long id;
    private Long productId;
    private Long customerId;
    private Long orderId;
    private String comment;
    private String status;
    private LocalDateTime commentDate;
}
