package com.example.TienDatShop.service.mapper;

import com.example.TienDatShop.dto.brand.BrandRequestDTO;
import com.example.TienDatShop.dto.brand.BrandResponseDTO;
import com.example.TienDatShop.entity.Brand;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface BrandMapper {

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Brand toEntity(BrandRequestDTO dto);

    BrandResponseDTO toDto(Brand entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateBrand(@MappingTarget Brand brand, BrandRequestDTO dto);


}
