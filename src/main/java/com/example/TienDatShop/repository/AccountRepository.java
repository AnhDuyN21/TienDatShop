package com.example.TienDatShop.repository;

import com.example.TienDatShop.entity.Accounts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Accounts, Long> {
    boolean existsByEmail(String email);
}
