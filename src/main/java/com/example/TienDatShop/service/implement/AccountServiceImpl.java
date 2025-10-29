package com.example.TienDatShop.service.implement;

import com.example.TienDatShop.repository.AccountRepository;
import com.example.TienDatShop.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;

}
