package com.internproject.productservice.mapper;

import com.internproject.productservice.dto.CategoryGetAllResponse;
import com.internproject.productservice.entity.Category;

public class CategoryMapper {
    private static CategoryMapper INSTANCE;

    public static CategoryMapper getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new CategoryMapper();
        }
        return INSTANCE;
    }

    public CategoryGetAllResponse toDTO(Category category) {
        CategoryGetAllResponse response = new CategoryGetAllResponse();
        response.setCategoryName(category.getCategoryName());
        response.setId(category.getId());

        return response;
    }
}
