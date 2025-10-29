package com.example.TienDatShop.dto.customer;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerRequestDTO {
    private String name;
    private String email;
    private String phone;
    private String password;
    private String address;
}

