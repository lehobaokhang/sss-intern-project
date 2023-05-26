package com.internproject.orderservice.dto.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
    private String id;
    private String productName;
    private byte[] productImage;
    private String productSize;
    private int productWeight;
    private CategoryDTO category;
    private int price;
    private int quantity;
    private String sellerId;
    private String sellerFullName;
}
