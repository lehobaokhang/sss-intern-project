package com.internproject.orderservice.dto.cart;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartDTO {
    private String cartId;
    private String productName;
    private byte[] productImage;
    private String[] details;
    private int price;
    private int quantity;
}
