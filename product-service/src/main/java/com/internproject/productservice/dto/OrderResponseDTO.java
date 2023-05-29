package com.internproject.productservice.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class OrderResponseDTO {
    private String sellerId;
    private ProductDTO products;
}
