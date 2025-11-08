package com.example.TienDatShop.service.mapper;

import com.example.TienDatShop.dto.admin.AdminRequestDTO;
import com.example.TienDatShop.dto.admin.AdminResponseDTO;
import com.example.TienDatShop.entity.Admin;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = {AccountMapper.class})
public interface AdminMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "name", target = "account.name")
    @Mapping(source = "email", target = "account.email")
    @Mapping(source = "phone", target = "account.phone")
    @Mapping(source = "password", target = "account.password")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Admin toEntity(AdminRequestDTO dto);

    @Mapping(source = "account.name", target = "name")
    @Mapping(source = "account.email", target = "email")
    @Mapping(source = "account.phone", target = "phone")
    @Mapping(source = "account.password", target = "password")
    @Mapping(source = "account.status", target = "status")
    AdminResponseDTO toDto(Admin entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "account", source = "dto", qualifiedByName = "updateAccountDetailsWithAdmin")
    @Mapping(target = "id", ignore = true)
    void updateAdmin(@MappingTarget Admin admin, AdminRequestDTO dto);
}
