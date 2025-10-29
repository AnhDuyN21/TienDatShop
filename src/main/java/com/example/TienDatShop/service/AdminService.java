package com.example.TienDatShop.service;

import com.example.TienDatShop.dto.admin.AdminRequestDTO;
import com.example.TienDatShop.dto.admin.AdminResponseDTO;

import java.util.List;


public interface AdminService {
    AdminResponseDTO getById(Long id);
    List<AdminResponseDTO> getAll();
    AdminResponseDTO create(AdminRequestDTO dto);
    AdminResponseDTO update(Long id, AdminRequestDTO dto);
}
