package com.internproject.productservice.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class ProductCsv {
    private String id;
    private String productName;
    private String productCategory;
    private int price;
    private int quantity;
    private String sellerId;
}
