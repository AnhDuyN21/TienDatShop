package com.example.TienDatShop.service.implement;


import com.example.TienDatShop.dto.order.OrderRequestDTO;
import com.example.TienDatShop.dto.order.OrderResponseDTO;
import com.example.TienDatShop.entity.Carts;
import com.example.TienDatShop.entity.Orders;
import com.example.TienDatShop.entity.Promotions;
import com.example.TienDatShop.entity.enumeration.OrderStatus;
import com.example.TienDatShop.repository.CartRepository;
import com.example.TienDatShop.repository.OrderRepository;
import com.example.TienDatShop.repository.PromotionRepository;
import com.example.TienDatShop.service.OrderService;
import com.example.TienDatShop.service.mapper.OrderMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository repository;
    private final CartRepository cartRepository;
    private final PromotionRepository promoRepository;
    private final OrderMapper mapper;

    @Override
    @Transactional
    public OrderResponseDTO create(OrderRequestDTO dto) {
        Carts cart = cartRepository.findById(dto.getCartId())
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        Orders order = new Orders();
        order.setCartId(cart);
        order.setCustomerId(cart.getCustomerId());
        order.setOrderDate(LocalDateTime.now());
        order.setTotalAmount(cart.getTotalAmount());
        order.setStatus(OrderStatus.CREATED);
        order.setPromotionCode(cart.getPromotionCode());

        Orders saved = repository.save(order);

        if (cart.getPromotionCode() != null && !cart.getPromotionCode().isEmpty()) {

            Promotions promo = promoRepository.findByCode(cart.getPromotionCode())
                    .orElseThrow(() -> new RuntimeException("Promotion code not found"));

            LocalDateTime now = LocalDateTime.now();

            if (now.isBefore(promo.getValidFrom()) || now.isAfter(promo.getValidTo())) {
                throw new RuntimeException("Promotion code is not valid");
            }

            if (promo.getUsageLimit() <= 0) {
                throw new RuntimeException("Promotion code has expired");
            }

            int currentUsageLimit = promo.getUsageLimit();
            promo.setUsageLimit(currentUsageLimit - 1);
        }
        return mapper.toDto(saved);
    }

    @Override
    public List<OrderResponseDTO> getAll() {
        return repository.findAll().stream()
                .map(mapper::toDto)
                .toList();
    }

    @Override
    public OrderResponseDTO getById(Long id) {
        Orders order = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        return mapper.toDto(order);
    }

    @Override
    @Transactional
    public OrderResponseDTO updateStatus(Long id, OrderStatus newStatus) {
        Orders order = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        order.setStatus(newStatus);
        Orders saved = repository.save(order);

        return mapper.toDto(saved);
    }
}
