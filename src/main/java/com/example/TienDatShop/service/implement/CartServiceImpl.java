package com.example.TienDatShop.service.implement;

import com.example.TienDatShop.dto.cart.CartRequestDTO;
import com.example.TienDatShop.dto.cart.CartResponseDTO;
import com.example.TienDatShop.dto.cart.cartItem.CartItemRequestDTO;
import com.example.TienDatShop.entity.*;
import com.example.TienDatShop.entity.enumeration.CartStatus;
import com.example.TienDatShop.exception.BadRequestException;
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
        Cart cart = mapper.toEntity(dto);

        setupCartRelationships(cart, dto);

        BigDecimal total = calculateTotal(cart, dto);

        cart.setTotalAmount(total);
        cart.setStatus(CartStatus.WAITING);
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
                .orElseThrow(() -> new BadRequestException("Cart not found"));
    }

    @Override
    @Transactional
    public CartResponseDTO update(Long id, CartRequestDTO dto) {
        Cart existing = cartRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("Cart not found"));

        mapper.updateCart(existing, dto);

        Customer customer = customerRepository.findById(dto.getCustomerId())
                .orElseThrow(() -> new BadRequestException("Customer not found"));
        existing.setCustomer(customer);

        updateCartItems(existing, dto);

        BigDecimal total = calculateTotal(existing, dto);

        existing.setTotalAmount(total);
        existing = cartRepository.save(existing);

        return mapper.toDto(existing);
    }

    @Override
    @Transactional
    public CartResponseDTO approve(Long id) {
        Cart cart = cartRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("cart not found"));
        if (!cart.getStatus().equals(CartStatus.WAITING)) {
            throw new BadRequestException(
                    "Cannot approve cart. Current status: " + cart.getStatus()
            );
        }
        cart.setStatus(CartStatus.APPROVED);
        cartRepository.save(cart);
        return mapper.toDto(cart);
    }


    private void setupCartRelationships(Cart cart, CartRequestDTO dto) {
        Customer customer = customerRepository.findById(dto.getCustomerId())
                .orElseThrow(() -> new BadRequestException("Customer not found with id : " + dto.getCustomerId()));
        cart.setCustomer(customer);

        for (int i = 0; i < dto.getItems().size(); i++) {
            CartItem itemEntity = cart.getItems().get(i);
            Long productId = dto.getItems().get(i).getProductId();

            Product managedProduct = entityManager.getReference(Product.class, productId);

            itemEntity.setProduct(managedProduct);
            itemEntity.setCart(cart);
        }
    }

    private BigDecimal calculateTotal(Cart cart, CartRequestDTO dto) {
        BigDecimal total = dto.getItems().stream()
                .map(i -> i.getPriceAtPurchase().multiply(BigDecimal.valueOf(i.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        if (dto.getPromotionCode() != null && !dto.getPromotionCode().isBlank()) {
            Promotion promo = promotionRepository.findByCode(dto.getPromotionCode())
                    .orElseThrow(() -> new BadRequestException("Promotion code not found"));

            LocalDateTime now = LocalDateTime.now();
            if (now.isBefore(promo.getValidFrom()) || now.isAfter(promo.getValidTo())) {
                throw new BadRequestException("Promotion code is not valid");
            }
            if (promo.getUsageLimit() <= 0) {
                throw new BadRequestException("Promotion code has expired");
            }

            BigDecimal discount = total
                    .multiply(promo.getDiscountPercent())
                    .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
            total = total.subtract(discount);

            cart.setPromotionCode(promo.getCode());
        } else {
            cart.setPromotionCode(null);
        }

        return total;
    }

    private void updateCartItems(Cart existingCart, CartRequestDTO dto) {
        existingCart.getItems().clear();

        for (CartItemRequestDTO itemDto : dto.getItems()) {
            Product product = productRepository.findById(itemDto.getProductId())
                    .orElseThrow(() -> new BadRequestException("Product not found"));

            CartItem item = new CartItem();
            item.setProduct(product);
            item.setQuantity(itemDto.getQuantity());
            item.setPriceAtPurchase(itemDto.getPriceAtPurchase());
            item.setCart(existingCart);

            existingCart.getItems().add(item);
        }
    }
}
