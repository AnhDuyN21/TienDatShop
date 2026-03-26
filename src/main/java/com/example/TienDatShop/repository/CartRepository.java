package com.example.TienDatShop.repository;

import com.example.TienDatShop.entity.Cart;
import com.example.TienDatShop.entity.enumeration.CartStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findByCustomerIdAndStatus(Long customerId, CartStatus status);
}
