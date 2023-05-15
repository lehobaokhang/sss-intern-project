package com.internproject.productservice.dto;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class UpdateCategoryRequest {
    @NotNull
    private String id;

    @NotNull
    private String categoryName;
}
