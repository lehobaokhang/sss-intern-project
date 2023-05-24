package com.internproject.productservice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CreateAndUpdateProductRequest {
    private String productName;
    private String productSize;
    private int productWeight;
    private String category;
    private int price;
    private int quantity;

//    private Map<String, Set<OptionDetailDTO>> options;
}
