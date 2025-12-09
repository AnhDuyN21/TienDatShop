package com.example.TienDatShop.service.implement;


import com.example.TienDatShop.dto.cart.cartItem.CartItemResponseDTO;
import com.example.TienDatShop.dto.order.OrderRequestDTO;
import com.example.TienDatShop.dto.order.OrderResponseDTO;
import com.example.TienDatShop.entity.*;
import com.example.TienDatShop.entity.enumeration.CartStatus;
import com.example.TienDatShop.exception.BadRequestException;
import com.example.TienDatShop.repository.*;
import com.example.TienDatShop.service.OrderService;
import com.example.TienDatShop.service.mapper.OrderMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository repository;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepo;
    private final PromotionRepository promoRepository;
    private final ProductRepository productRepo;
    private final OrderMapper mapper;

    @Override
    @Transactional
    public OrderResponseDTO create(OrderRequestDTO dto) {
        Cart cart = cartRepository.findById(dto.getCartId())
                .orElseThrow(() -> new BadRequestException("Cart not found"));

        if (cart.getStatus() != CartStatus.APPROVED) {
            throw new BadRequestException("Cart is not in active state for ordering.");
        }

        Order order = mapper.mapCartToOrder(cart);
        order.setOrderDate(LocalDateTime.now());

        processInventory(cart);

        if (cart.getPromotionCode() != null && !cart.getPromotionCode().isEmpty()) {
            updatePromotionUsage(cart);
        }
        Order saved = repository.save(order);

        cart.setStatus(CartStatus.COMPLETED);
        cartRepository.save(cart);
        List<CartItem> cartItems = cart.getItems();
        return mapOrderToDto(saved, cartItems);
    }

    @Override
    public List<OrderResponseDTO> getAll() {

        List<Order> orders = repository.findAll();

        List<Long> cartIds = orders.stream()
                .map(order -> order.getCart().getId())
                .collect(Collectors.toList());

        List<CartItem> allCartItems = cartItemRepo.findByCartIdInWithProduct(cartIds);


        Map<Long, List<CartItem>> itemsByCartId = allCartItems.stream()
                .collect(Collectors.groupingBy(item -> item.getCart().getId()));


        List<OrderResponseDTO> responseList = new ArrayList<>();

        for (Order order : orders) {
            Long currentCartId = order.getCart().getId();

            List<CartItem> cartItemsForOrder = itemsByCartId.getOrDefault(currentCartId, Collections.emptyList());

            OrderResponseDTO response = mapOrderToDto(order, cartItemsForOrder);
            responseList.add(response);
        }

        return responseList;
    }

    @Override
    public OrderResponseDTO getById(Long id) {
        Order order = repository.findById(id)
                .orElseThrow(() -> new BadRequestException("Order not found"));

        List<CartItem> listCartItem = cartItemRepo.getByCartId(order.getCart().getId());

        return mapOrderToDto(order, listCartItem);
    }

    @Transactional
    protected void processInventory(Cart cart) {
        for (CartItem item : cart.getItems()) {
            Long productId = item.getProduct().getId();


            Product product = productRepo.findByIdWithLock(productId)
                    .orElseThrow(() -> new BadRequestException("Product not found"));

            int orderedQuantity = item.getQuantity();


            int productQuantity = product.getDetail().getStockQuantity();
            if (productQuantity < orderedQuantity) {

                throw new RuntimeException("Product '" + product.getName() + "' is out of stock.");
            }


            product.getDetail().setStockQuantity(productQuantity - orderedQuantity);

        }
    }

    @Transactional
    protected void updatePromotionUsage(Cart cart) {


        Promotion promo = promoRepository.findByCodeForUpdate(cart.getPromotionCode())
                .orElseThrow(() -> new BadRequestException("Promotion code not found"));

        if (promo.getUsageLimit() <= 0) {
            throw new BadRequestException("Promotion code has expired.");
        }

        promo.setUsageLimit(promo.getUsageLimit() - 1);
        promoRepository.save(promo);
    }

    private List<CartItemResponseDTO> convertCartItemsToDetails(List<CartItem> cartItems) {
        List<CartItemResponseDTO> details = new ArrayList<>();

        for (CartItem item : cartItems) {
            CartItemResponseDTO itemDto = new CartItemResponseDTO();
            itemDto.setProductId(item.getProduct().getId());
            itemDto.setQuantity(item.getQuantity());
            itemDto.setPriceAtPurchase(item.getPriceAtPurchase());

            details.add(itemDto);
        }
        return details;
    }

    private OrderResponseDTO mapOrderToDto(Order order, List<CartItem> cartItems) {

        List<CartItemResponseDTO> details = convertCartItemsToDetails(cartItems);


        OrderResponseDTO response = new OrderResponseDTO();
        response.setId(order.getId());
        response.setCustomerId(order.getCustomer().getId());
        response.setPromotionCode(order.getPromotionCode());
        response.setOrderDate(order.getOrderDate());
        response.setTotalAmount(order.getTotalAmount());
        response.setStatus(order.getStatus());
        response.setDetails(details);

        return response;
    }
}
