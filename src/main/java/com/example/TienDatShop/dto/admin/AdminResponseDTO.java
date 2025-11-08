package com.example.TienDatShop.dto.admin;

import com.example.TienDatShop.entity.enumeration.AccountStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminResponseDTO {
    private Long id;
    private String name;
    private String email;
    private String phone;
    private String password;
    private AccountStatus status;
}
