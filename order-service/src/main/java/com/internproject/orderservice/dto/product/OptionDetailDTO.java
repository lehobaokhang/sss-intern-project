package com.internproject.orderservice.dto.product;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OptionDetailDTO that = (OptionDetailDTO) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}