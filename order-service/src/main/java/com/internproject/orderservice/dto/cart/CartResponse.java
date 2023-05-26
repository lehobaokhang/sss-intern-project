package com.internproject.orderservice.dto.cart;

import com.internproject.orderservice.dto.product.CategoryDTO;
import com.internproject.orderservice.dto.product.ProductDTO;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class CartResponse {
    private String cartId;
    private int quantity;
    private ProductDTO productDTO;
}
