package com.example.TienDatShop.service;

import com.example.TienDatShop.dto.customer.CustomerRequestDTO;
import com.example.TienDatShop.dto.customer.CustomerResponseDTO;

import java.util.List;

public interface CustomerService {
    CustomerResponseDTO create(CustomerRequestDTO dto);

    CustomerResponseDTO update(Long id, CustomerRequestDTO dto);

    CustomerResponseDTO getById(Long id);

    List<CustomerResponseDTO> getAll();
}

