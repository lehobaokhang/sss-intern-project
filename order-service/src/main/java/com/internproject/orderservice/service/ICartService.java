package com.internproject.orderservice.service;

import com.internproject.orderservice.dto.AddToCartDTO;
import com.internproject.orderservice.dto.ProductDTO;
import com.internproject.orderservice.entity.Cart;

public interface ICartService {
    public Cart addCart(AddToCartDTO addToCartDTO, String userId);
}
