package com.example.TienDatShop.controller;

import com.example.TienDatShop.dto.cart.CartRequestDTO;
import com.example.TienDatShop.dto.cart.CartResponseDTO;
import com.example.TienDatShop.service.CartService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/carts")
public class CartController {
    private final CartService cartService;

    @PostMapping
    public ResponseEntity<CartResponseDTO> create(@RequestBody CartRequestDTO dto) {
        return ResponseEntity.ok(cartService.createCart(dto));
    }

    @GetMapping
    public ResponseEntity<List<CartResponseDTO>> getAll() {
        return ResponseEntity.ok(cartService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CartResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(cartService.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CartResponseDTO> update(@PathVariable Long id, @RequestBody CartRequestDTO dto) {
        return ResponseEntity.ok(cartService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        cartService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
