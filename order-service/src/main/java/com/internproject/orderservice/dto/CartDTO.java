package com.internproject.orderservice.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class CartDTO {
    private String id;
    private String userId;
    private String productId;
    private int quantity;
    private int price;
    private String productName;
    private byte[] productImage;
}
