package com.example.TienDatShop.dto.brand;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BrandRequestDTO {
    private String name;
    private String address;
    private String phone;
    private String email;
}
