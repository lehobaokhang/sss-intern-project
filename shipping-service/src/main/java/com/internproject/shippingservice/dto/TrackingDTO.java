package com.internproject.shippingservice.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.internproject.shippingservice.entity.District;
import lombok.*;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class TrackingDTO {
    @JsonIgnore
    private String id;
    private Date trackingAt;
    private String trackingDistrict;
}
