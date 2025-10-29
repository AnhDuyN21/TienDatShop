package com.example.TienDatShop.service.mapper;

import com.example.TienDatShop.dto.customer.CustomerRequestDTO;
import com.example.TienDatShop.dto.customer.CustomerResponseDTO;
import com.example.TienDatShop.entity.Accounts;
import com.example.TienDatShop.entity.Customers;
import com.example.TienDatShop.entity.enumeration.CustomerStatus;
import org.springframework.stereotype.Component;


@Component
public class CustomerMapper {

    public CustomerResponseDTO toDto(Customers entity) {
        if (entity == null) return null;

        Accounts account = entity.getAccountId();

        return CustomerResponseDTO.builder()
                .name(account != null ? account.getName() : null)
                .email(account != null ? account.getEmail() : null)
                .phone(account != null ? account.getPhone() : null)
                .password(account != null ? account.getPassword() : null)
                .status(entity.getStatus() != null ? entity.getStatus().name() : null)
                .build();
    }

    public Customers toEntity(CustomerRequestDTO dto) {
        if (dto == null) return null;

        Accounts account = Accounts.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .phone(dto.getPhone())
                .password(dto.getPassword())
                .build();

        return Customers.builder()
                .accountId(account)
                .status(CustomerStatus.ACTIVE)
                .build();
    }

    // Cập nhật entity từ DTO (chỉ update các field không null)
    public void updateCustomerFromDto(CustomerRequestDTO dto, Customers customer) {
        if (dto == null || customer == null) return;

        Accounts account = customer.getAccountId();
        if (account == null) {
            account = Accounts.builder().build();
            customer.setAccountId(account);
        }

        setIfNotNull(dto.getName(), account::setName);
        setIfNotNull(dto.getEmail(), account::setEmail);
        setIfNotNull(dto.getPhone(), account::setPhone);
        setIfNotNull(dto.getPassword(), account::setPassword);
    }

    // Helper để set field nếu không null
    private <T> void setIfNotNull(T value, java.util.function.Consumer<T> setter) {
        if (value != null) setter.accept(value);
    }

}

