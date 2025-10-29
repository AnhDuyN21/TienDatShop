package com.example.TienDatShop.service.implement;

import com.example.TienDatShop.dto.admin.AdminRequestDTO;
import com.example.TienDatShop.dto.admin.AdminResponseDTO;
import com.example.TienDatShop.entity.Admins;
import com.example.TienDatShop.repository.AccountRepository;
import com.example.TienDatShop.repository.AdminRepository;
import com.example.TienDatShop.service.AdminService;
import com.example.TienDatShop.service.mapper.AdminMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {
    private final AccountRepository accountRepository;
    private final AdminRepository adminRepository;
    private final AdminMapper adminMapper;

    @Override
    public AdminResponseDTO getById(Long id) {
        Admins admin = adminRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Admin not found with id: " + id));
        return adminMapper.toDto(admin);
    }

    @Override
    public List<AdminResponseDTO> getAll() {
        List<Admins> admins = adminRepository.findAll();
        return admins.stream()
                .map(adminMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public AdminResponseDTO create(AdminRequestDTO dto) {
        if (accountRepository.existsByEmail(dto.getEmail())) throw new IllegalArgumentException("Email đã tồn tại!");

        // Map DTO → Entity
        Admins admin = adminMapper.toEntity(dto);

        // Lưu entity
        admin = adminRepository.save(admin);
        return adminMapper.toDto(admin);
    }

    @Override
    @Transactional
    public AdminResponseDTO update(Long id, AdminRequestDTO dto) {
        Admins admin = adminRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        if(dto.getEmail() != null && accountRepository.existsByEmail(dto.getEmail())){
            throw new IllegalArgumentException("Email đã tồn tại!");
        }
        // Map cập nhật các field từ DTO sang entity hiện có
        adminMapper.updateAdminFromDto(dto, admin);

        // Lưu entity
        admin = adminRepository.save(admin);

        return adminMapper.toDto(admin);
    }

}
