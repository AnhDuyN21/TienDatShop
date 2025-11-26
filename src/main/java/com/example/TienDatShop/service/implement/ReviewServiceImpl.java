package com.example.TienDatShop.service.implement;

import com.example.TienDatShop.dto.review.ReviewRequestDTO;
import com.example.TienDatShop.dto.review.ReviewResponseDTO;
import com.example.TienDatShop.entity.Order;
import com.example.TienDatShop.entity.Review;
import com.example.TienDatShop.entity.enumeration.ReviewStatus;
import com.example.TienDatShop.exception.BadRequestException;
import com.example.TienDatShop.repository.OrderRepository;
import com.example.TienDatShop.repository.ReviewRepository;
import com.example.TienDatShop.service.ReviewService;
import com.example.TienDatShop.service.mapper.ReviewMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository repository;
    private final OrderRepository orderRepository;
    private final ReviewMapper mapper;


    @Override
    @Transactional
    public ReviewResponseDTO create(ReviewRequestDTO dto) {
        Review review = mapper.toEntity(dto);
        Order order = orderRepository.findById(review.getOrder().getId())
                .orElseThrow(() -> new BadRequestException("Order not found"));
        //check customer id
        if (!order.getCustomer().getId().equals(review.getCustomer().getId()))
            throw new BadRequestException("Customer id: " + dto.getCustomerId() + " not match with customer id in order");
        review.setCommentDate(dto.getCommentDate() != null ? dto.getCommentDate() : LocalDateTime.now());
        review.setStatus(ReviewStatus.PUBLISHED);
        return mapper.toDto(repository.save(review));
    }

    @Override
    public List<ReviewResponseDTO> getAll() {
        return repository.findAll().stream()
                .map(mapper::toDto)
                .toList();
    }

    @Override
    public ReviewResponseDTO getById(Long id) {
        Review review = repository.findById(id)
                .orElseThrow(() -> new BadRequestException("Review not found"));
        return mapper.toDto(review);
    }

    @Override
    @Transactional
    public ReviewResponseDTO update(Long id, ReviewRequestDTO dto) {
        Review review = repository.findById(id)
                .orElseThrow(() -> new BadRequestException("Review not found"));

        if (dto.getComment() != null) review.setComment(dto.getComment());
        if (dto.getStatus() != null) review.setStatus(dto.getStatus());
        review.setCommentDate(dto.getCommentDate() != null ? dto.getCommentDate() : LocalDateTime.now());

        return mapper.toDto(repository.save(review));
    }

    @Override
    public List<ReviewResponseDTO> getByProductId(Long productId) {
        List<Review> reviews = repository.findByProductId_Id(productId);
        return reviews.stream()
                .map(mapper::toDto)
                .toList();
    }
}
