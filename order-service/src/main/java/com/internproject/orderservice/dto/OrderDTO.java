package com.internproject.orderservice.dto;

import lombok.*;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class OrderDTO {
    private String id;
    private String userId;
    private int priceTotal;
    private int shippingFee;
    private Date createdAt;
    private List<OrderProductDTO> orderProductsDTO;
}
