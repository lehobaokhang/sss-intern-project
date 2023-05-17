package com.internproject.orderservice.dto.cart;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UpdateCartDTO {
    @NotNull
    private String[] details;

    @NotNull
    private int quantity;
}
