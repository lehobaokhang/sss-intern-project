package com.internproject.shippingservice.dto;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class ShipDTO {
    private String id;
    private String orderId;
    private String status;
    private List<TrackingDTO> tracking;
}
