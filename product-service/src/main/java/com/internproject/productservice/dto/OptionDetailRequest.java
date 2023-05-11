package com.internproject.productservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OptionDetailRequest {
    private String option_detail_name;
    private int option_detail_price;
    private int option_detail_quantity;
}