package com.internproject.shippingservice.dto;

import lombok.*;

import java.util.Date;

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
}