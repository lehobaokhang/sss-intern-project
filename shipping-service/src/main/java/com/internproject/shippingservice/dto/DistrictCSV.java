package com.internproject.shippingservice.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class DistrictCSV {
    private int id;
    private String districtName;
    private String districtFullName;
    private int provinceId;
}
