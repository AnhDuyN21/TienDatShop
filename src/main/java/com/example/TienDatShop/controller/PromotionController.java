package com.example.TienDatShop.controller;

import com.example.TienDatShop.dto.promotion.PromotionRequestDTO;
import com.example.TienDatShop.dto.promotion.PromotionResponseDTO;
import com.example.TienDatShop.service.PromotionService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/promotions")
@AllArgsConstructor
public class PromotionController {

    private final PromotionService service;

    @PostMapping
    public ResponseEntity<PromotionResponseDTO> create(@Valid @RequestBody PromotionRequestDTO dto) {
        return ResponseEntity.ok(service.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PromotionResponseDTO> update(@PathVariable Long id, @Valid @RequestBody PromotionRequestDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build(); // chuẩn REST: 204 No Content
    }

    @GetMapping("/{id}")
    public ResponseEntity<PromotionResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<PromotionResponseDTO>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }
}
