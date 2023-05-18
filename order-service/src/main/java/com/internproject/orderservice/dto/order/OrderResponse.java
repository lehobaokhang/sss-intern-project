package com.internproject.orderservice.dto.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponse {
    private String id;
    private int shippingFee;
    private int priceTotal;
    private Date createdAt;
    private List<OrderProductResponse> orderProducts;
}
