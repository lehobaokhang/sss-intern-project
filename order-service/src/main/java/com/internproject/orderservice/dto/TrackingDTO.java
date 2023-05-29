package com.internproject.orderservice.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class TrackingDTO {
    private String id;
    private String locate;
}
