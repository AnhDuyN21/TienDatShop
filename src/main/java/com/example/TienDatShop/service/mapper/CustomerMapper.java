package com.example.TienDatShop.service.mapper;

import com.example.TienDatShop.dto.customer.CustomerRequestDTO;
import com.example.TienDatShop.dto.customer.CustomerResponseDTO;
import com.example.TienDatShop.entity.Customer;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = {AccountMapper.class})
public interface CustomerMapper {

    @Mapping(source = "name", target = "account.name")
    @Mapping(source = "email", target = "account.email")
    @Mapping(source = "phone", target = "account.phone")
    @Mapping(source = "password", target = "account.password")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Customer toEntity(CustomerRequestDTO dto);

    @Mapping(source = "account.name", target = "name")
    @Mapping(source = "account.email", target = "email")
    @Mapping(source = "account.phone", target = "phone")
    @Mapping(source = "account.password", target = "password")
    @Mapping(source = "account.status", target = "status")
    CustomerResponseDTO toDto(Customer entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "account", source = "dto", qualifiedByName = "updateAccountDetailsWithCustomer")
    void updateCustomer(@MappingTarget Customer customer, CustomerRequestDTO dto);
}
