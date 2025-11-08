package com.example.TienDatShop.service;

import com.example.TienDatShop.dto.order.OrderRequestDTO;
import com.example.TienDatShop.dto.order.OrderResponseDTO;

import java.util.List;

public interface OrderService {
    OrderResponseDTO create(OrderRequestDTO dto);

    List<OrderResponseDTO> getAll();

    OrderResponseDTO getById(Long id);
}
