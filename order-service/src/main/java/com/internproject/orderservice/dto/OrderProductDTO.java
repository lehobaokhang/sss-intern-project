package com.internproject.orderservice.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class OrderProductDTO {
    private String id;
    private String productId;
    private int quantity;
    private int price;
}
