package com.example.TienDatShop.controller;

import com.example.TienDatShop.dto.image.ImageRequestDTO;
import com.example.TienDatShop.dto.image.ImageResponseDTO;
import com.example.TienDatShop.service.ImageService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/images")
public class ImageController {

    private final ImageService service;

    @PostMapping
    public ResponseEntity<List<ImageResponseDTO>> create(@RequestBody ImageRequestDTO dto) {
        return ResponseEntity.ok(service.createImages(dto));
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<List<ImageResponseDTO>> getByProduct(@PathVariable Long productId) {
        return ResponseEntity.ok(service.getByProduct(productId));
    }
}