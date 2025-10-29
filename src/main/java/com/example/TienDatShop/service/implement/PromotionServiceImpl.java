package com.example.TienDatShop.service.implement;

import com.example.TienDatShop.dto.promotion.PromotionRequestDTO;
import com.example.TienDatShop.dto.promotion.PromotionResponseDTO;
import com.example.TienDatShop.entity.Promotions;
import com.example.TienDatShop.repository.PromotionRepository;
import com.example.TienDatShop.service.PromotionService;
import com.example.TienDatShop.service.mapper.PromotionMapper;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PromotionServiceImpl implements PromotionService {

    private final PromotionRepository repository;
    private final PromotionMapper mapper;

    @Override
    @Transactional
    public PromotionResponseDTO create(@Valid PromotionRequestDTO dto) {
        validatePromotion(dto, null);

        Promotions entity = mapper.toEntity(dto);
        return mapper.toDto(repository.save(entity));
    }

    @Override
    @Transactional
    public PromotionResponseDTO update(Long id, @Valid PromotionRequestDTO dto) {
        Promotions existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Promotion not found"));

        validatePromotion(dto, id); // kiểm tra code & ngày hợp lệ

        existing.setDiscountPercent(dto.getDiscountPercent());
        existing.setUsageLimit(dto.getUsageLimit());
        existing.setValidFrom(dto.getValidFrom());
        existing.setValidTo(dto.getValidTo());
        existing.setCode(dto.getCode());

        return mapper.toDto(repository.save(existing));
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public PromotionResponseDTO getById(Long id) {
        return repository.findById(id)
                .map(mapper::toDto)
                .orElseThrow(() -> new RuntimeException("Promotion not found"));
    }

    @Override
    public List<PromotionResponseDTO> getAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toDto)
                .toList();
    }

    private void validatePromotion(PromotionRequestDTO dto, Long currentId) {

        if (dto.getValidFrom().isAfter(dto.getValidTo())) {
            throw new RuntimeException("Invalid date range: validFrom must be before validTo");
        }

        repository.findByCode(dto.getCode()).ifPresent(existing -> {
            if (currentId == null || !existing.getId().equals(currentId)) {
                throw new RuntimeException("Promotion code already exists");
            }
        });
    }
}
