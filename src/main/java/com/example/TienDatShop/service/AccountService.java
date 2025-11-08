package com.example.TienDatShop.service;

import com.example.TienDatShop.dto.account.LoginRequestDTO;

public interface AccountService {
    String verify(LoginRequestDTO dto);
}
