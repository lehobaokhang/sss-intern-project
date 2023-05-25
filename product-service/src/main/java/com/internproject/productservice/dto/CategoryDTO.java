package com.internproject.productservice.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.internproject.productservice.entity.Product;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class CategoryDTO {
    private String id;
    private String categoryName;
    @JsonIgnore
    private List<Product> products;
}
