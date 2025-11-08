package com.example.TienDatShop.repository;

import com.example.TienDatShop.entity.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Long> {
    boolean existsByEmail(String email);

    boolean existsByPhone(String phone);
}
