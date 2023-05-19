package com.internproject.orderservice.dto.cart;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartResponseDTO {
    private String cartId;
    private String productName;
    private byte[] productImage;
    private int price;
    private int quantity;
    private String sellerId;
    private String category;
}
