package com.internproject.orderservice.dto.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderProductResponse {
    private String productName;
    private String[] productImage;
    private int quantity;
    private int price;
}
