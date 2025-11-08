package com.example.TienDatShop.repository;

import com.example.TienDatShop.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    List<CartItem> getByCartId(Long cartId);

    @Query("SELECT ci FROM CartItem ci " +
            "JOIN FETCH ci.product p " +
            "WHERE ci.cart.id IN :cartIds")
        // Sử dụng IN để tải hàng loạt
    List<CartItem> findByCartIdInWithProduct(@Param("cartIds") List<Long> cartIds);
}
