package com.example.TienDatShop.service.implement;

import com.example.TienDatShop.dto.cart.CartRequestDTO;
import com.example.TienDatShop.dto.cart.CartResponseDTO;
import com.example.TienDatShop.dto.cart.cartItem.CartItemRequestDTO;
import com.example.TienDatShop.entity.*;
import com.example.TienDatShop.entity.enumeration.CartStatus;
import com.example.TienDatShop.repository.CartRepository;
import com.example.TienDatShop.repository.CustomerRepository;
import com.example.TienDatShop.repository.ProductRepository;
import com.example.TienDatShop.repository.PromotionRepository;
import com.example.TienDatShop.service.CartService;
import com.example.TienDatShop.service.mapper.CartItemMapper;
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
    private final CartItemMapper cartItemMapper;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public CartResponseDTO createCart(CartRequestDTO dto) {
        Cart cart = mapper.toEntity(dto);

        setupCartRelationships(cart, dto);

        BigDecimal total = calculateTotal(cart, dto);

        cart.setTotalAmount(total);
        cart.setStatus(CartStatus.ACTIVE);
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
        Cart existing = cartRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cart not found"));
        
        mapper.updateCart(existing, dto);

        Customer customer = customerRepository.findById(dto.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        existing.setCustomer(customer);

        // Cập nhật Items
        updateCartItems(existing, dto);

        // Tính toán Tổng tiền và Khuyến mãi
        BigDecimal total = calculateTotal(existing, dto);

        existing.setTotalAmount(total);
        existing = cartRepository.save(existing);

        return mapper.toDto(existing);
    }

    private void setupCartRelationships(Cart cart, CartRequestDTO dto) {
        // 1. Gán Customer Entity
        Customer customer = customerRepository.findById(dto.getCustomerId())
                .orElseThrow(() -> new IllegalArgumentException("Customer not found with id : " + dto.getCustomerId()));
        cart.setCustomer(customer);

        // 2. Gán Product Reference và Cart cho từng CartItem Entity
        // Sử dụng vòng lặp for để truy cập cả DTO (lấy ID) và Entity (gán Product)
        for (int i = 0; i < dto.getItems().size(); i++) {
            CartItem itemEntity = cart.getItems().get(i);
            Long productId = dto.getItems().get(i).getProductId();

            // Tối ưu: Tạo managed entity reference (Proxy) thay vì tải toàn bộ Entity
            Product managedProduct = entityManager.getReference(Product.class, productId);

            // Gán mối quan hệ
            itemEntity.setProduct(managedProduct);
            itemEntity.setCart(cart);
        }
    }

    private BigDecimal calculateTotal(Cart cart, CartRequestDTO dto) {
        // 1. Tính tổng tiền trước khuyến mãi
        BigDecimal total = dto.getItems().stream()
                .map(i -> i.getPriceAtPurchase().multiply(BigDecimal.valueOf(i.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // 2. Kiểm tra và áp dụng khuyến mãi (nếu có)
        if (dto.getPromotionCode() != null && !dto.getPromotionCode().isBlank()) {
            Promotion promo = promotionRepository.findByCode(dto.getPromotionCode())
                    .orElseThrow(() -> new RuntimeException("Promotion code not found"));

            // 2a. Kiểm tra trạng thái khuyến mãi
            LocalDateTime now = LocalDateTime.now();
            if (now.isBefore(promo.getValidFrom()) || now.isAfter(promo.getValidTo())) {
                throw new RuntimeException("Promotion code is not valid");
            }
            if (promo.getUsageLimit() <= 0) {
                throw new RuntimeException("Promotion code has expired");
            }

            // 2b. Tính toán giảm giá
            BigDecimal discount = total
                    .multiply(promo.getDiscountPercent())
                    .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
            total = total.subtract(discount);

            // 2c. Set promotion code vào Cart Entity
            cart.setPromotionCode(promo.getCode());
        } else {
            // Đảm bảo mã khuyến mãi bị xóa nếu người dùng không gửi mã
            cart.setPromotionCode(null);
        }

        return total;
    }

    private void updateCartItems(Cart existingCart, CartRequestDTO dto) {
        // 1. Xóa item cũ (orphanRemoval = true sẽ xóa trong DB khi save)
        existingCart.getItems().clear();

        // 2. Thêm item mới thủ công
        for (CartItemRequestDTO itemDto : dto.getItems()) {
            Product product = productRepository.findById(itemDto.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));

            CartItem item = new CartItem();
            item.setProduct(product);
            item.setQuantity(itemDto.getQuantity());
            item.setPriceAtPurchase(itemDto.getPriceAtPurchase());
            item.setCart(existingCart); // Quan trọng

            existingCart.getItems().add(item);
        }
    }
}
