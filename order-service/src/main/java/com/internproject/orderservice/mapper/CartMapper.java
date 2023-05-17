package com.internproject.orderservice.mapper;

import com.internproject.orderservice.dto.cart.AddCartDTO;
import com.internproject.orderservice.dto.cart.CartDTO;
import com.internproject.orderservice.dto.product.OptionDetailDTO;
import com.internproject.orderservice.dto.product.ProductDTO;
import com.internproject.orderservice.entity.Cart;

import java.util.Arrays;
import java.util.Set;

public class CartMapper {
    private static CartMapper INSTANCE;

    public static CartMapper getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new CartMapper();
        }
        return INSTANCE;
    }

    public Cart toEntity(AddCartDTO addToCartDTO) {
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
        cartDTO.setPrice(cart.getPrice());
        cartDTO.setDetails(cart.getDetails());

        return cartDTO;
    }
}
