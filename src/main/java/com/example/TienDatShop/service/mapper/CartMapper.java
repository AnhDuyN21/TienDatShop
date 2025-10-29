package com.example.TienDatShop.service.mapper;

import com.example.TienDatShop.dto.cart.CartRequestDTO;
import com.example.TienDatShop.dto.cart.CartResponseDTO;
import com.example.TienDatShop.dto.cart.cartItem.CartItemRequestDTO;
import com.example.TienDatShop.dto.cart.cartItem.CartItemResponseDTO;
import com.example.TienDatShop.entity.CartItems;
import com.example.TienDatShop.entity.Carts;
import com.example.TienDatShop.entity.Customers;
import com.example.TienDatShop.entity.Products;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CartMapper {

    // Helper set if not null
    private <T> void setIfNotNull(T value, java.util.function.Consumer<T> setter) {
        if (value != null) setter.accept(value);
    }

    // RequestDTO -> Carts entity
    public Carts toEntity(CartRequestDTO dto) {
        if (dto == null) return null;

        Carts cart = new Carts();
        cart.setPromotionCode(dto.getPromotionCode());
        cart.setCustomerId(mapCustomer(dto.getCustomerId()));

        if (dto.getItems() != null) {
            List<CartItems> items = new ArrayList<>();
            for (CartItemRequestDTO itemDto : dto.getItems()) {
                CartItems item = new CartItems();
                item.setProductId(map(itemDto.getProductId()));
                item.setQuantity(itemDto.getQuantity());
                item.setPriceAtPurchase(itemDto.getPriceAtPurchase());
                item.setCart(cart); // set back reference
                items.add(item);
            }
            cart.setItems(items);
        }

        return cart;
    }

    // Carts entity -> ResponseDTO
    public CartResponseDTO toDto(Carts cart) {
        if (cart == null) return null;

        List<CartItemResponseDTO> itemDTOs = new ArrayList<>();
        if (cart.getItems() != null) {
            for (CartItems item : cart.getItems()) {
                CartItemResponseDTO dto = new CartItemResponseDTO();
                dto.setProductId(item.getProductId() != null ? item.getProductId().getId() : null);
                dto.setQuantity(item.getQuantity());
                dto.setPriceAtPurchase(item.getPriceAtPurchase());
                itemDTOs.add(dto);
            }
        }

        CartResponseDTO response = new CartResponseDTO();
        response.setId(cart.getId());
        response.setTotalAmount(cart.getTotalAmount());
        response.setPromotionCode(cart.getPromotionCode());
        response.setCustomerId(cart.getCustomerId() != null ? cart.getCustomerId().getId() : null);
        response.setItems(itemDTOs);

        return response;
    }

    // Map Long -> Products
    public Products map(Long id) {
        if (id == null) return null;
        Products p = new Products();
        p.setId(id);
        return p;
    }

    // Map Products -> Long
    public Long map(Products product) {
        if (product == null) return null;
        return product.getId();
    }

    // Map Long -> Customers
    public Customers mapCustomer(Long id) {
        if (id == null) return null;
        Customers c = new Customers();
        c.setId(id);
        return c;
    }

    // Map Customers -> Long
    public Long mapCustomer(Customers customer) {
        if (customer == null) return null;
        return customer.getId();
    }
}