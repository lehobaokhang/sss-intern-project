package com.internproject.orderservice.mapper;

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

    public Cart toEntity(ProductDTO productDTO, String userId, int quantity) {
        Cart cart = new Cart();
        cart.setProductId(productDTO.getId());
        cart.setPrice(productDTO.getPrice());
        cart.setQuantity(quantity);
        cart.setUserId(userId);
        return cart;
    }

    public CartResponseDTO toDTO(Cart cart, ProductDTO productDTO) {
        CartResponseDTO cartDTO = new CartResponseDTO();
        cartDTO.setCartId(cartDTO.getCartId());
        cartDTO.setQuantity(cart.getQuantity());
        cartDTO.setProductName(productDTO.getProductName());
        cartDTO.setPrice(productDTO.getPrice());
        cartDTO.setProductImage(productDTO.getProductImage());
        cartDTO.setCategory(productDTO.getCategory());
        cartDTO.setSellerId(productDTO.getSellerId());
        cartDTO.setSellerFullName(productDTO.getSellerFullName());

        return cartDTO;
    }
}
