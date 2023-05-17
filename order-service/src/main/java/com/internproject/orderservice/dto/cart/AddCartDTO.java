package com.internproject.orderservice.dto.cart;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AddCartDTO {
    @NotNull
    private String productId;

    @NotNull
    private String[] details;

    @NotNull
    private int quantity;
}
