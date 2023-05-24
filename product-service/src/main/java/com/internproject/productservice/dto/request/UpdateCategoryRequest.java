package com.internproject.productservice.dto.request;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdateCategoryRequest {
    @NotNull
    private String id;

    @NotNull
    private String categoryName;
}
