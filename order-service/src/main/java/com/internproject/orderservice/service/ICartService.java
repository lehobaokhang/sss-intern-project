package com.internproject.orderservice.service;

import com.internproject.orderservice.dto.cart.CartRequestDTO;
import com.internproject.orderservice.dto.cart.CartResponseDTO;
import com.internproject.orderservice.entity.Cart;

import java.util.List;

public interface ICartService {
    public Cart addCart(CartRequestDTO addToCartDTO, String userId);

    public List<CartResponseDTO> getAll(String userId);

    public void deleteCart(String id);

    public Cart getCartById(String cartId);

    public Cart updateCart(String id, CartRequestDTO cartRequestDTO);
}