package com.internproject.orderservice.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class ShipDTO {
    private String id;
    private String orderId;
    private String status;
}
