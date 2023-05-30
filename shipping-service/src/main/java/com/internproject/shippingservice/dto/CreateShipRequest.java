package com.internproject.shippingservice.dto;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class CreateShipRequest {
    private List<ShipDTO> ships;
}
