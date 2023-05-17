package com.internproject.orderservice.dto.order;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class OrderDTO {
    private List<String> cartId;
}
