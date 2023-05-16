package com.internproject.orderservice.service;

import com.internproject.orderservice.dto.AddAndUpdateCartDTO;
import com.internproject.orderservice.dto.CartDTO;
import com.internproject.orderservice.entity.Cart;

import java.util.List;

public interface ICartService {
    public Cart addCart(AddAndUpdateCartDTO addToCartDTO, String userId);

    public Cart updateCart(AddAndUpdateCartDTO updateCartDTO, String userId);

    public List<CartDTO> getAll(String userId);

    public void deleteCart(String id);
}