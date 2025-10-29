package com.example.TienDatShop.service.implement;

import com.example.TienDatShop.dto.review.ReviewRequestDTO;
import com.example.TienDatShop.dto.review.ReviewResponseDTO;
import com.example.TienDatShop.entity.Reviews;
import com.example.TienDatShop.entity.enumeration.ReviewStatus;
import com.example.TienDatShop.repository.CustomerRepository;
import com.example.TienDatShop.repository.OrderRepository;
import com.example.TienDatShop.repository.ProductRepository;
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
    private final ProductRepository productRepository;
    private final CustomerRepository customerRepository;
    private final OrderRepository orderRepository;
    private final ReviewMapper mapper;


    @Override
    @Transactional
    public ReviewResponseDTO create(ReviewRequestDTO dto) {
        Reviews review = mapper.toEntity(dto);

        review.setProductId(productRepository.findById(dto.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found")));

        review.setCustomerId(customerRepository.findById(dto.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Customer not found")));

        review.setOrderId(orderRepository.findById(dto.getOrderId())
                .orElseThrow(() -> new RuntimeException("Order not found")));

        review.setCommentDate(dto.getCommentDate() != null ? dto.getCommentDate() : LocalDateTime.now());

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
        Reviews review = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Review not found"));
        return mapper.toDto(review);
    }

    @Override
    @Transactional
    public ReviewResponseDTO update(Long id, ReviewRequestDTO dto) {
        Reviews review = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Review not found"));

        if (dto.getComment() != null) review.setComment(dto.getComment());
        if (dto.getStatus() != null) review.setStatus(ReviewStatus.valueOf(dto.getStatus()));
        if (dto.getCommentDate() != null) review.setCommentDate(dto.getCommentDate());

        return mapper.toDto(repository.save(review));
    }

    @Override
    public List<ReviewResponseDTO> getByProductId(Long productId) {
        List<Reviews> reviews = repository.findByProductId_Id(productId);
        return reviews.stream()
                .map(mapper::toDto)
                .toList();
    }
}
