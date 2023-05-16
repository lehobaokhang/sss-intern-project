package com.internproject.orderservice.mapper;

import com.internproject.orderservice.dto.AddToCartDTO;
import com.internproject.orderservice.dto.CartDTO;
import com.internproject.orderservice.entity.Cart;

public class CartMapper {
    private static CartMapper INSTANCE;

    public static CartMapper getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new CartMapper();
        }
        return INSTANCE;
    }

    public Cart toEntity(AddToCartDTO addToCartDTO) {
        Cart cart = new Cart();
        cart.setQuantity(addToCartDTO.getQuantity());
        cart.setProductId(addToCartDTO.getProductId());
        return cart;
    }
}
