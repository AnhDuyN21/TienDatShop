package com.example.TienDatShop.service.implement;

import com.example.TienDatShop.dto.customer.CustomerRequestDTO;
import com.example.TienDatShop.dto.customer.CustomerResponseDTO;
import com.example.TienDatShop.entity.Customer;
import com.example.TienDatShop.entity.enumeration.AccountStatus;
import com.example.TienDatShop.repository.AccountRepository;
import com.example.TienDatShop.repository.CustomerRepository;
import com.example.TienDatShop.service.CustomerService;
import com.example.TienDatShop.service.mapper.CustomerMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(10);

    @Override
    public CustomerResponseDTO getById(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found with id: " + id));
        return customerMapper.toDto(customer);
    }

    @Override
    public List<CustomerResponseDTO> getAll() {
        List<Customer> customers = customerRepository.findAll();
        return customers.stream()
                .map(customerMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public CustomerResponseDTO create(CustomerRequestDTO dto) {

        // check trùng phone và email
        if (dto.getEmail() != null && accountRepository.existsByEmail(dto.getEmail())) {
            throw new IllegalArgumentException("Email đã được sử dụng");
        } else if (dto.getPhone() != null && accountRepository.existsByPhone(dto.getPhone())) {
            throw new IllegalArgumentException("Số điện thoại đã được sử dụng");
        }

        // Map DTO → Entity
        Customer customer = customerMapper.toEntity(dto);

        //Check account đã được tạo chưa + set status
        if (customer.getAccount() != null) customer.getAccount().setStatus(AccountStatus.ACTIVE);
        else throw new IllegalStateException("Không tạo được account");

        //Xử lí logic hash mật khẩu ( chưa làm )
        if (dto.getPassword() != null && !dto.getPassword().isEmpty())
            customer.getAccount().setPassword(encoder.encode(dto.getPassword()));

        // Lưu entity
        customer = customerRepository.save(customer);
        return customerMapper.toDto(customer);
    }

    @Override
    @Transactional
    public CustomerResponseDTO update(Long id, CustomerRequestDTO dto) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        // check trùng phone và email
        if (dto.getEmail() != null && accountRepository.existsByEmail(dto.getEmail())) {
            throw new IllegalArgumentException("Email đã được sử dụng");
        } else if (dto.getPhone() != null && accountRepository.existsByPhone(dto.getPhone())) {
            throw new IllegalArgumentException("Số điện thoại đã được sử dụng");
        }

        // Map cập nhật các field từ DTO sang entity hiện có
        customerMapper.updateCustomer(customer, dto);

        //Xử lí logic hash mật khẩu ( chưa làm )
        if (dto.getPassword() != null && !dto.getPassword().isEmpty())
            customer.getAccount().setPassword(dto.getPassword());

        // Lưu entity
        customer = customerRepository.save(customer);

        return customerMapper.toDto(customer);
    }
}

