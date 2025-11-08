package com.example.TienDatShop.repository;

import com.example.TienDatShop.entity.Image;
import com.example.TienDatShop.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
    List<Image> findByProductId(Product product);
}
