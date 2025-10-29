package com.example.TienDatShop.repository;

import com.example.TienDatShop.entity.Brands;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BrandRepository extends JpaRepository<Brands, Long> {
}
