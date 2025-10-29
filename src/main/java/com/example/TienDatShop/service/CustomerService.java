package com.example.TienDatShop.service;

import com.example.TienDatShop.dto.customer.*;

import java.util.List;

public interface CustomerService {
    CustomerResponseDTO create(CustomerRequestDTO dto);
    CustomerResponseDTO update(Long id, CustomerRequestDTO dto);
    void toggle(Long id);
    CustomerResponseDTO getById(Long id);
    List<CustomerResponseDTO> getAll();
}

