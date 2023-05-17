package com.internproject.orderservice.dto;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AddAndUpdateCartDTO {
    @NotNull
    private String productId;

    @NotNull
    private int quantity;
}