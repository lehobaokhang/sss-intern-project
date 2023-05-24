package com.internproject.productservice.dto;

import com.internproject.productservice.entity.Category;
import lombok.*;

import java.util.Map;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class ProductDTO {
    private String id;
    private String productName;
    private byte[] productImage;
    private String productSize;
    private int productWeight;
    private String sellerId;
    private Category category;
    private boolean deleted;
    private int price;
    private int quantity;
}
