package com.example.TienDatShop.service;

import com.example.TienDatShop.dto.brand.BrandRequestDTO;
import com.example.TienDatShop.dto.brand.BrandResponseDTO;

import java.util.List;

public interface BrandService {
    List<BrandResponseDTO> getAll();

    BrandResponseDTO getById(Long id);

    BrandResponseDTO create(BrandRequestDTO dto);

    BrandResponseDTO update(Long id, BrandRequestDTO dto);

}
