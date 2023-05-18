package com.internproject.productservice.dto;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class FindAllProductByIdRequest {
    @NotNull
    private List<String> productId;
}
