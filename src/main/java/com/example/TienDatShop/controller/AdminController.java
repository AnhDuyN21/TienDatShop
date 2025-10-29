package com.example.TienDatShop.controller;

import com.example.TienDatShop.dto.admin.AdminRequestDTO;
import com.example.TienDatShop.dto.admin.AdminResponseDTO;
import com.example.TienDatShop.service.AdminService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/admins")
public class AdminController {
    private final AdminService service;


    @PostMapping
    public ResponseEntity<AdminResponseDTO> create(@RequestBody AdminRequestDTO dto) {
        AdminResponseDTO response = service.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    @GetMapping
    public ResponseEntity<List<AdminResponseDTO>> getAll() {
        List<AdminResponseDTO> response = service.getAll();
        return ResponseEntity.ok(response);
    }


    @GetMapping("/{id}")
    public ResponseEntity<AdminResponseDTO> getById(@PathVariable Long id) {
        AdminResponseDTO response = service.getById(id);
        return ResponseEntity.ok(response);
    }


    @PutMapping("/{id}")
    public ResponseEntity<AdminResponseDTO> update(@PathVariable Long id, @RequestBody AdminRequestDTO dto) {
        AdminResponseDTO response = service.update(id, dto);
        return ResponseEntity.ok(response);
    }

}
