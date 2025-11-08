package com.example.TienDatShop.service;

import com.example.TienDatShop.dto.cart.CartRequestDTO;
import com.example.TienDatShop.dto.cart.CartResponseDTO;

import java.util.List;

public interface CartService {
    CartResponseDTO createCart(CartRequestDTO dto);

    List<CartResponseDTO> getAll();

    CartResponseDTO getById(Long id);

    CartResponseDTO update(Long id, CartRequestDTO dto);

}
