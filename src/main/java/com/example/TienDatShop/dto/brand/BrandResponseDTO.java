package com.example.TienDatShop.dto.brand;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BrandResponseDTO {
    private Long id;
    private String name;
    private String address;
    private String phone;
    private String email;
}
