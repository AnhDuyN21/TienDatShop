package com.example.TienDatShop.repository;

import com.example.TienDatShop.entity.Product;
import com.example.TienDatShop.entity.ProductDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductDetailRepository extends JpaRepository<ProductDetail, Long> {
    Optional<ProductDetail> findByProductId(Product product);
}
