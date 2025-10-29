package com.example.TienDatShop.service.mapper;

import com.example.TienDatShop.dto.admin.AdminRequestDTO;
import com.example.TienDatShop.dto.admin.AdminResponseDTO;
import com.example.TienDatShop.entity.Accounts;
import com.example.TienDatShop.entity.Admins;
import org.springframework.stereotype.Component;

@Component
public class AdminMapper {

    // Helper để set field nếu không null
    private <T> void setIfNotNull(T value, java.util.function.Consumer<T> setter) {
        if (value != null) setter.accept(value);
    }

    // Entity → ResponseDTO
    public AdminResponseDTO toDto(Admins entity) {
        if (entity == null) return null;

        Accounts account = entity.getAccountId();

        return AdminResponseDTO.builder()
                .name(account != null ? account.getName() : null)
                .email(account != null ? account.getEmail() : null)
                .phone(account != null ? account.getPhone() : null)
                .password(account != null ? account.getPassword() : null)
                .build();
    }

    // RequestDTO → Entity (tạo mới)
    public Admins toEntity(AdminRequestDTO dto) {
        if (dto == null) return null;

        Accounts account = Accounts.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .phone(dto.getPhone())
                .password(dto.getPassword())
                .build();

        return Admins.builder()
                .accountId(account)
                .build();
    }

    // Cập nhật entity từ DTO (chỉ update field không null)
    public void updateAdminFromDto(AdminRequestDTO dto, Admins admin) {
        if (dto == null || admin == null) return;

        Accounts account = admin.getAccountId();
        if (account == null) {
            account = Accounts.builder().build();
            admin.setAccountId(account);
        }

        setIfNotNull(dto.getName(), account::setName);
        setIfNotNull(dto.getEmail(), account::setEmail);
        setIfNotNull(dto.getPhone(), account::setPhone);
        setIfNotNull(dto.getPassword(), account::setPassword);
    }
}