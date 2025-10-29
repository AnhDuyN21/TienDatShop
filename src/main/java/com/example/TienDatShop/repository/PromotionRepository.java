package com.example.TienDatShop.repository;

import com.example.TienDatShop.entity.Promotions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PromotionRepository extends JpaRepository<Promotions, Long> {
    Optional<Promotions> findByCode(String code);

    boolean existsByCode(String code);
}
