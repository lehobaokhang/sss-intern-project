package com.internproject.orderservice.dto;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class ShipDTO {
    private String id;
    private String orderId;
    private String status;
    private List<TrackingDTO> tracking;
}
