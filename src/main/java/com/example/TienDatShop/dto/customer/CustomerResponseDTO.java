package com.example.TienDatShop.dto.customer;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerResponseDTO {
    private Long id;
    private String name;
    private String email;
    private String phone;
    private String password;
    private String address;
    private String status;
}

