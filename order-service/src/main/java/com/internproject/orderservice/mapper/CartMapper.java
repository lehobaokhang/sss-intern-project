package com.internproject.orderservice.mapper;

import com.internproject.orderservice.dto.AddAndUpdateCartDTO;
import com.internproject.orderservice.dto.CartDTO;
import com.internproject.orderservice.dto.ProductDTO;
import com.internproject.orderservice.entity.Cart;

public class CartMapper {
    private static CartMapper INSTANCE;

    public static CartMapper getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new CartMapper();
        }
        return INSTANCE;
    }

    public Cart toEntity(AddAndUpdateCartDTO addToCartDTO) {
        Cart cart = new Cart();
        cart.setQuantity(addToCartDTO.getQuantity());
        cart.setProductId(addToCartDTO.getProductId());
        return cart;
    }

    public CartDTO toDTO(Cart cart, ProductDTO productDTO) {
        CartDTO cartDTO = new CartDTO();
        cartDTO.setCartId(cart.getId());
        cartDTO.setQuantity(cart.getQuantity());
        cartDTO.setProductImage(productDTO.getProductImage());
        cartDTO.setProductName(productDTO.getProductName());

        return cartDTO;
    }
}
