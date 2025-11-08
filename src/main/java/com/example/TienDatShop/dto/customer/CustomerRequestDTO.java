package com.example.TienDatShop.dto.customer;

import com.example.TienDatShop.entity.enumeration.AccountStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerRequestDTO {
    private String name;
    private String email;
    private String phone;
    private String password;
    private String address;
    private AccountStatus status;
}

