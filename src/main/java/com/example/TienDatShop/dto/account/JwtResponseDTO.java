package com.example.TienDatShop.dto.account;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JwtResponseDTO {
    private String token;
    private Long userId;
    private String role;
    private String email;
    private String expiresAt;
}
