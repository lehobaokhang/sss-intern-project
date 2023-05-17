package com.internproject.orderservice.service;

import com.internproject.orderservice.dto.cart.AddCartDTO;
import com.internproject.orderservice.dto.cart.CartDTO;
import com.internproject.orderservice.entity.Cart;

import java.util.List;

public interface ICartService {
    public Cart addCart(AddCartDTO addToCartDTO, String userId);

    public List<CartDTO> getAll(String userId);

    public void deleteCart(String id);

    public Cart getCartById(String cartId);
}