package com.example.TienDatShop.service.implement;

import com.example.TienDatShop.dto.cart.CartRequestDTO;
import com.example.TienDatShop.dto.cart.CartResponseDTO;
import com.example.TienDatShop.dto.cart.cartItem.CartItemRequestDTO;
import com.example.TienDatShop.entity.*;
import com.example.TienDatShop.repository.CartRepository;
import com.example.TienDatShop.repository.CustomerRepository;
import com.example.TienDatShop.repository.ProductRepository;
import com.example.TienDatShop.repository.PromotionRepository;
import com.example.TienDatShop.service.CartService;
import com.example.TienDatShop.service.mapper.CartMapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
    private final CartRepository cartRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;
    private final PromotionRepository promotionRepository;
    private final CartMapper mapper;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public CartResponseDTO createCart(CartRequestDTO dto) {
        Carts cart = mapper.toEntity(dto);

        // ✅ Gắn lại customer thành managed entity (tránh detached)
        Customers managedCustomer = entityManager.getReference(Customers.class, dto.getCustomerId());
        cart.setCustomerId(managedCustomer);

        // ✅ Gắn lại product cho từng CartItem (tránh detached tương tự)
        Carts finalCart = cart;
        cart.getItems().forEach(item -> {
            Products managedProduct = entityManager.getReference(Products.class, item.getProductId().getId());
            item.setProductId(managedProduct);
            item.setCart(finalCart);
        });

        // 💸 Tính tổng tiền
        BigDecimal total = dto.getItems().stream()
                .map(i -> i.getPriceAtPurchase().multiply(BigDecimal.valueOf(i.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // 🎟️ Kiểm tra promotion (nếu có)
        if (dto.getPromotionCode() != null && !dto.getPromotionCode().isBlank()) {
            Promotions promo = promotionRepository.findByCode(dto.getPromotionCode())
                    .orElseThrow(() -> new RuntimeException("Promotion code not found"));

            LocalDateTime now = LocalDateTime.now();
            if (now.isBefore(promo.getValidFrom()) || now.isAfter(promo.getValidTo())) {
                throw new RuntimeException("Promotion code is not valid");
            }

            if (promo.getUsageLimit() <= 0) {
                throw new RuntimeException("Promotion code has expired");
            }

            BigDecimal discount = total
                    .multiply(promo.getDiscountPercent())
                    .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);

            total = total.subtract(discount);


            cart.setPromotionCode(promo.getCode());
        }

        cart.setTotalAmount(total);
        cart = cartRepository.save(cart);

        return mapper.toDto(cart);
    }

    @Override
    public List<CartResponseDTO> getAll() {
        return cartRepository.findAll().stream().map(mapper::toDto).toList();
    }

    @Override
    public CartResponseDTO getById(Long id) {
        return cartRepository.findById(id)
                .map(mapper::toDto)
                .orElseThrow(() -> new RuntimeException("Cart not found"));
    }

    @Override
    @Transactional
    public CartResponseDTO update(Long id, CartRequestDTO dto) {
        Carts existing = cartRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        // ✅ Gán lại customer (rất quan trọng)
        Customers customer = customerRepository.findById(dto.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        existing.setCustomerId(customer);

        existing.setPromotionCode(dto.getPromotionCode());

        // ✅ Xóa item cũ an toàn (nếu orphanRemoval chưa có)
        existing.getItems().clear();

        for (CartItemRequestDTO itemDto : dto.getItems()) {
            Products product = productRepository.findById(itemDto.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));

            CartItems item = new CartItems();
            item.setProductId(product);
            item.setQuantity(itemDto.getQuantity());
            item.setPriceAtPurchase(itemDto.getPriceAtPurchase());
            item.setCart(existing);

            existing.getItems().add(item);
        }

        BigDecimal total = dto.getItems().stream()
                .map(i -> i.getPriceAtPurchase().multiply(BigDecimal.valueOf(i.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        if (dto.getPromotionCode() != null && !dto.getPromotionCode().isBlank()) {
            Promotions promo = promotionRepository.findByCode(dto.getPromotionCode())
                    .orElseThrow(() -> new RuntimeException("Promotion code not found"));

            LocalDateTime now = LocalDateTime.now();

            if (now.isBefore(promo.getValidFrom()) || now.isAfter(promo.getValidTo())) {
                throw new RuntimeException("Promotion code is not valid");
            }

            if (promo.getUsageLimit() <= 0) {
                throw new RuntimeException("Promotion code has expired");
            }

            BigDecimal discount = total
                    .multiply(promo.getDiscountPercent())
                    .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
            total = total.subtract(discount);
        }

        existing.setTotalAmount(total);

        // ✅ Save lại cart (JPA sẽ cascade xuống items)
        existing = cartRepository.save(existing);

        return mapper.toDto(existing);
    }


    @Override
    public void delete(Long id) {
        cartRepository.deleteById(id);
    }
}
