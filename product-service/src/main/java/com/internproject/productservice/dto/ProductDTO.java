package com.internproject.productservice.dto;

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

//    private Map<String, Set<OptionDetailDTO>> options;
}
