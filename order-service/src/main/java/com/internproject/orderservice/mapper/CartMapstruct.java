package com.internproject.orderservice.mapper;

import com.internproject.orderservice.dto.CartDTO;
import com.internproject.orderservice.entity.Cart;
import org.mapstruct.Mapper;

@Mapper
public interface CartMapstruct {
    CartDTO toCartDTO(Cart cart);
    Cart toCart(CartDTO cartDTO);
}
