package com.example.TienDatShop.repository;

import com.example.TienDatShop.entity.Images;
import com.example.TienDatShop.entity.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<Images, Long> {
    List<Images> findByProductId(Products product);
}
