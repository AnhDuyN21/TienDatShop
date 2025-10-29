package com.example.TienDatShop.service.implement;

import com.example.TienDatShop.dto.customer.CustomerRequestDTO;
import com.example.TienDatShop.dto.customer.CustomerResponseDTO;
import com.example.TienDatShop.entity.Customers;
import com.example.TienDatShop.entity.enumeration.CustomerStatus;
import com.example.TienDatShop.repository.AccountRepository;
import com.example.TienDatShop.repository.CustomerRepository;
import com.example.TienDatShop.service.CustomerService;
import com.example.TienDatShop.service.mapper.CustomerMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final AccountRepository accountRepository;
    private final CustomerMapper customerMapper;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);

    @Override
    public CustomerResponseDTO getById(Long id) {
        Customers customer = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found with id: " + id));
        return customerMapper.toDto(customer);
    }

    @Override
    public List<CustomerResponseDTO> getAll() {
        List<Customers> customers = customerRepository.findAll();
        return customers.stream()
                .map(customerMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public CustomerResponseDTO create(CustomerRequestDTO dto) {
        if (accountRepository.existsByEmail(dto.getEmail())) throw new IllegalArgumentException("Email đã tồn tại!");

        // Map DTO → Entity
        Customers customer = customerMapper.toEntity(dto);

        // ✅ Mã hoá password tại đây
        String encodedPassword = passwordEncoder.encode(customer.getAccountId().getPassword());
        customer.getAccountId().setPassword(encodedPassword);

        // Lưu entity
        customer = customerRepository.save(customer);
        return customerMapper.toDto(customer);
    }

    @Override
    @Transactional
    public CustomerResponseDTO update(Long id, CustomerRequestDTO dto) {
        Customers customer = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        if (dto.getEmail() != null && accountRepository.existsByEmail(dto.getEmail())) {
            throw new IllegalArgumentException("Email đã tồn tại!");
        }
        // Map cập nhật các field từ DTO sang entity hiện có
        customerMapper.updateCustomerFromDto(dto, customer);

        // Lưu entity
        customer = customerRepository.save(customer);

        return customerMapper.toDto(customer);
    }

    @Override
    @Transactional
    public void toggle(Long id) {
        Customers customer = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found with id: " + id));
        if (customer.getStatus().equals(CustomerStatus.ACTIVE)) customer.setStatus(CustomerStatus.INACTIVE);
        else customer.setStatus(CustomerStatus.ACTIVE);
        customerRepository.save(customer);
    }
}

