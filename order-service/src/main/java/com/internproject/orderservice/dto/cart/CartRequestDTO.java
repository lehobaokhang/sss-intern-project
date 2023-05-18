package com.internproject.orderservice.dto.cart;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CartRequestDTO {
    private String productId;
    @NotNull
    private int quantity;
}
