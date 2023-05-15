package com.internproject.productservice.dto;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public class OptionDetailDTO {
    private String id;
    @NotNull
    private String option_detail_name;
    @NotNull
    private int option_detail_price;
    @NotNull
    private int option_detail_quantity;
}