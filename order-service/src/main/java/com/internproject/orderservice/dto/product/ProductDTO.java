package com.internproject.orderservice.dto.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
    private String id;
    private String productName;
    private byte[] productImage;
    private String productSize;
    private int productWeight;
    private String category;
    private int price;
    private int quantity;
    private String sellerId;
    private String sellerFullName;
}
