package com.example.TienDatShop.repository;

import com.example.TienDatShop.entity.ProductDetails;
import com.example.TienDatShop.entity.Products;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductDetailRepository extends JpaRepository<ProductDetails, Long> {
    Optional<ProductDetails> findByProductId(Products product);
}
