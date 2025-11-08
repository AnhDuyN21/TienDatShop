package com.example.TienDatShop.service;

import com.example.TienDatShop.dto.promotion.PromotionRequestDTO;
import com.example.TienDatShop.dto.promotion.PromotionResponseDTO;

import java.util.List;

public interface PromotionService {
    PromotionResponseDTO create(PromotionRequestDTO dto);

    PromotionResponseDTO update(Long id, PromotionRequestDTO dto);

    PromotionResponseDTO getById(Long id);

    List<PromotionResponseDTO> getAll();
}
