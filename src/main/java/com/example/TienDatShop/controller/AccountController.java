package com.example.TienDatShop.controller;

import com.example.TienDatShop.dto.account.LoginRequestDTO;
import com.example.TienDatShop.dto.customer.CustomerRequestDTO;
import com.example.TienDatShop.dto.customer.CustomerResponseDTO;
import com.example.TienDatShop.entity.Account;
import com.example.TienDatShop.service.AccountService;
import com.example.TienDatShop.service.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/auth")
public class AccountController {
    private final CustomerService customerService;
    private final AccountService accountService;

    @PostMapping("/register/customer")
    public ResponseEntity<CustomerResponseDTO> registerCustomer(@RequestBody CustomerRequestDTO dto) {
        CustomerResponseDTO response = customerService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequestDTO dto) {
        String response = accountService.verify(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/me")
    public ResponseEntity<Account> getCurrentAccountInfo() {
        Account response = accountService.getCurrentAccount();
        return ResponseEntity.ok(response);
    }
}
