package com.internproject.productservice.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class RatingDTO {
    private String userId;
    private int rating;
    private String review;
}
