package com.example.TienDatShop.service;


import com.example.TienDatShop.dto.review.ReviewRequestDTO;
import com.example.TienDatShop.dto.review.ReviewResponseDTO;

import java.util.List;

public interface ReviewService {
    ReviewResponseDTO create(ReviewRequestDTO dto);

    List<ReviewResponseDTO> getAll();

    ReviewResponseDTO getById(Long id);

    ReviewResponseDTO update(Long id, ReviewRequestDTO dto);

    List<ReviewResponseDTO> getByProductId(Long productId);
}
