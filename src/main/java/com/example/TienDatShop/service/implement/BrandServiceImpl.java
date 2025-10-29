package com.example.TienDatShop.service.implement;

import com.example.TienDatShop.dto.brand.BrandRequestDTO;
import com.example.TienDatShop.dto.brand.BrandResponseDTO;
import com.example.TienDatShop.entity.Brands;
import com.example.TienDatShop.repository.BrandRepository;
import com.example.TienDatShop.service.BrandService;
import com.example.TienDatShop.service.mapper.BrandMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BrandServiceImpl implements BrandService {

    private final BrandRepository repository;
    private final BrandMapper mapper;

    @Override
    public List<BrandResponseDTO> getAll() {
        return repository.findAll().stream()
                .map(mapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public BrandResponseDTO getById(Long id) {
        return repository.findById(id)
                .map(mapper::toResponseDTO)
                .orElseThrow(() -> new RuntimeException("Brand not found"));
    }

    @Override
    @Transactional
    public BrandResponseDTO create(BrandRequestDTO dto) {
        Brands brand = mapper.toEntity(dto);
        repository.save(brand);
        return mapper.toResponseDTO(brand);
    }

    @Override
    @Transactional
    public BrandResponseDTO update(Long id, BrandRequestDTO dto) {
        Brands brand = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Brand not found"));

        mapper.updateEntityFromDTO(dto, brand); // patch update
        repository.save(brand);
        return mapper.toResponseDTO(brand);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
