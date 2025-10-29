package com.example.TienDatShop.service.mapper;

import com.example.TienDatShop.dto.review.ReviewRequestDTO;
import com.example.TienDatShop.dto.review.ReviewResponseDTO;
import com.example.TienDatShop.entity.Customers;
import com.example.TienDatShop.entity.Orders;
import com.example.TienDatShop.entity.Products;
import com.example.TienDatShop.entity.Reviews;
import com.example.TienDatShop.entity.enumeration.ReviewStatus;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ReviewMapper {

    // Entity -> DTO
    public ReviewResponseDTO toDto(Reviews review) {
        if (review == null) return null;

        return ReviewResponseDTO.builder()
                .id(review.getId())
                .customerId(review.getCustomerId() != null ? review.getCustomerId().getId() : null)
                .productId(review.getProductId() != null ? review.getProductId().getId() : null)
                .orderId(review.getOrderId() != null ? review.getOrderId().getId() : null)
                .comment(review.getComment())
                .status(String.valueOf(review.getStatus()))
                .commentDate(review.getCommentDate())
                .build();
    }

    // DTO -> Entity (partial, dùng trong service)
    public Reviews toEntity(ReviewRequestDTO dto) {
        if (dto == null) return null;

        Reviews review = new Reviews();
        review.setComment(dto.getComment());
        review.setStatus(dto.getStatus() != null ? dto.getStatus() : ReviewStatus.PUBLISHED);
        review.setCommentDate(LocalDateTime.now());

        // productId, customerId, orderId sẽ set trong service
        return review;
    }

    // Helpers map IDs -> entities (dùng khi cần)
    public Customers mapCustomer(Long id) {
        if (id == null) return null;
        Customers c = new Customers();
        c.setId(id);
        return c;
    }

    public Products mapProduct(Long id) {
        if (id == null) return null;
        Products p = new Products();
        p.setId(id);
        return p;
    }

    public Orders mapOrder(Long id) {
        if (id == null) return null;
        Orders o = new Orders();
        o.setId(id);
        return o;
    }
}
