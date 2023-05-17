package com.internproject.orderservice.dto.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartResponse {
    private String id;
    private int priceTotal;
    private Date createdAt;
}
