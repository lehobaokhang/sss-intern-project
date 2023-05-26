package com.internproject.shippingservice.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class TrackingDTO {
    private String id;
    private String locate;
}
