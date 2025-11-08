package com.example.TienDatShop.dto.admin;

import com.example.TienDatShop.entity.enumeration.AccountStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminRequestDTO {
    private String name;
    private String email;
    private String phone;
    private String password;
    private AccountStatus status;
}
