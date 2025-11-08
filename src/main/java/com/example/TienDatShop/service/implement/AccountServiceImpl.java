package com.example.TienDatShop.service.implement;

import com.example.TienDatShop.dto.account.LoginRequestDTO;
import com.example.TienDatShop.repository.AccountRepository;
import com.example.TienDatShop.service.AccountService;
import com.example.TienDatShop.util.JWTUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    private final AuthenticationManager authManager;
    private final JWTUtil jwtUtil;

    @Override
    public String verify(LoginRequestDTO dto) {
        Authentication authentication = authManager.authenticate(new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword()));
        if (authentication.isAuthenticated()) return jwtUtil.generateToken(dto.getEmail());
        return "Login fail!";
    }
}
