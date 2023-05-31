package com.internproject.orderservice.mapper;

import com.internproject.orderservice.dto.CartDTO;
import com.internproject.orderservice.entity.Cart;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper
public interface CartMapstruct {
    @Mapping(target = "productName", ignore = true)
    @Mapping(target = "productImage", ignore = true)
    CartDTO toCartDTO(Cart cart);

    Cart toCart(CartDTO cartDTO);
}
