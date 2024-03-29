package com.internproject.productservice.dto;

import lombok.*;

import java.util.List;

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
    private CategoryDTO category;
    private boolean deleted;
    private int price;
    private int quantity;
    private List<RatingDTO> rate;
}
