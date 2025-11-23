package com.example.TienDatShop.service;

import com.example.TienDatShop.dto.account.LoginRequestDTO;
import com.example.TienDatShop.entity.Account;

public interface AccountService {
    String verify(LoginRequestDTO dto);

    Account getCurrentAccount();
}
