package com.internproject.orderservice.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class CategoryDTO {
    private String id;
    private String categoryName;
}
