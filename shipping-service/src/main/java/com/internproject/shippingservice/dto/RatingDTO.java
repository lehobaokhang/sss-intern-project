package com.internproject.shippingservice.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class RatingDTO {
    private String id;
    private String userId;
    private String productId;
    private int rating;
    private String review;
}
