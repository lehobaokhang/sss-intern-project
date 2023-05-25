package com.internproject.productservice.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.internproject.productservice.entity.Category;
import lombok.*;

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
    @JsonIgnore
    private boolean deleted;
    private int price;
    private int quantity;
//    private String sellerFullName;
}
