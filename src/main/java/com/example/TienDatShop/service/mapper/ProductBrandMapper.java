package com.example.TienDatShop.service.mapper;

import com.example.TienDatShop.entity.Brand;
import com.example.TienDatShop.entity.Product;
import com.example.TienDatShop.entity.ProductBrand;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductBrandMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(source = "product", target = "product")
    @Mapping(source = "brand", target = "brand")
    ProductBrand toEntity(Product product, Brand brand);
}
