package com.example.TienDatShop.service.mapper;

import com.example.TienDatShop.dto.admin.AdminRequestDTO;
import com.example.TienDatShop.dto.customer.CustomerRequestDTO;
import com.example.TienDatShop.entity.Account;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface AccountMapper {
    //AdminMapper gọi
    @Named("updateAccountDetailsWithAdmin")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateAccountFromAdminDto(AdminRequestDTO dto, @MappingTarget Account account);

    @Named("updateAccountDetailsWithCustomer")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateAccountFromCustomerDto(CustomerRequestDTO dto, @MappingTarget Account account);
}
