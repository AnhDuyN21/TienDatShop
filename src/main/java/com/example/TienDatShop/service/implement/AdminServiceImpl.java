package com.example.TienDatShop.service.implement;

import com.example.TienDatShop.dto.admin.AdminRequestDTO;
import com.example.TienDatShop.dto.admin.AdminResponseDTO;
import com.example.TienDatShop.entity.Admin;
import com.example.TienDatShop.entity.enumeration.AccountStatus;
import com.example.TienDatShop.exception.BadRequestException;
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
        Admin admin = adminRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("Admin not found with id: " + id));
        return adminMapper.toDto(admin);
    }

    @Override
    public List<AdminResponseDTO> getAll() {
        List<Admin> admins = adminRepository.findAll();
        return admins.stream()
                .map(adminMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public AdminResponseDTO create(AdminRequestDTO dto) {
        if (accountRepository.existsByEmail(dto.getEmail())) throw new IllegalArgumentException("Email đã tồn tại!");

        // check trùng phone và email
        if (dto.getEmail() != null && accountRepository.existsByEmail(dto.getEmail())) {
            throw new BadRequestException("Email đã được sử dụng");
        } else if (dto.getPhone() != null && accountRepository.existsByPhone(dto.getPhone())) {
            throw new BadRequestException("Số điện thoại đã được sử dụng");
        }

        // Map DTO → Entity
        Admin admin = adminMapper.toEntity(dto);

        //Check account đã được tạo chưa + set status
        if (admin.getAccount() != null) admin.getAccount().setStatus(AccountStatus.ACTIVE);
        else throw new BadRequestException("Không tạo được account");

        //Xử lí logic hash mật khẩu ( chưa làm )
        if (dto.getPassword() != null && !dto.getPassword().isEmpty())
            admin.getAccount().setPassword(dto.getPassword());

        admin = adminRepository.save(admin);
        return adminMapper.toDto(admin);
    }

    @Override
    @Transactional
    public AdminResponseDTO update(Long id, AdminRequestDTO dto) {
        Admin admin = adminRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("Không tìm thấy admin với ID :" + id));

        // check trùng phone và email
        if (dto.getEmail() != null && accountRepository.existsByEmail(dto.getEmail())) {
            throw new BadRequestException("Email đã được sử dụng");
        } else if (dto.getPhone() != null && accountRepository.existsByPhone(dto.getPhone())) {
            throw new BadRequestException("Số điện thoại đã được sử dụng");
        }
        adminMapper.updateAdmin(admin, dto);

        //Xử lí logic hash mật khẩu ( chưa làm )
        if (dto.getPassword() != null && !dto.getPassword().isEmpty())
            admin.getAccount().setPassword(dto.getPassword());

        admin = adminRepository.save(admin);
        return adminMapper.toDto(admin);
    }

}
