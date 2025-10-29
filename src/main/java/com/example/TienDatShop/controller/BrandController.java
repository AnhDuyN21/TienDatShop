package com.example.TienDatShop.controller;

import com.example.TienDatShop.dto.brand.BrandRequestDTO;
import com.example.TienDatShop.dto.brand.BrandResponseDTO;
import com.example.TienDatShop.service.BrandService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/brands")
public class BrandController {
    private final BrandService service;

    @GetMapping
    public ResponseEntity<List<BrandResponseDTO>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BrandResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @PostMapping
    public ResponseEntity<BrandResponseDTO> create(@RequestBody BrandRequestDTO dto) {
        return ResponseEntity.ok(service.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BrandResponseDTO> update(@PathVariable Long id, @RequestBody BrandRequestDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}

