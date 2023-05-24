package com.internproject.productservice.dto;

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
}
