package com.internproject.orderservice.mapper;

import com.internproject.orderservice.dto.cart.CartRequestDTO;
import com.internproject.orderservice.dto.cart.CartResponseDTO;
import com.internproject.orderservice.dto.product.ProductDTO;
import com.internproject.orderservice.entity.Cart;

public class CartMapper {
    private static CartMapper INSTANCE;

    public static CartMapper getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new CartMapper();
        }
        return INSTANCE;
    }

    public Cart toEntity(CartRequestDTO dto) {
        Cart cart = new Cart();
        cart.setProductId(dto.getProductId());
        cart.setQuantity(dto.getQuantity());
        return cart;
    }

    public CartResponseDTO toDTO(Cart cart) {
        CartResponseDTO cartDTO = new CartResponseDTO();
        cartDTO.setCartId(cart.getId());
        cartDTO.setCartId(cartDTO.getCartId());
        cartDTO.setQuantity(cart.getQuantity());
        return cartDTO;
    }
}
