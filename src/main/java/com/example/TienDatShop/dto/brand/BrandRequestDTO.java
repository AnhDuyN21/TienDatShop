package com.example.TienDatShop.dto.brand;

import com.example.TienDatShop.entity.enumeration.BrandStatus;
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
    private BrandStatus status;
}
