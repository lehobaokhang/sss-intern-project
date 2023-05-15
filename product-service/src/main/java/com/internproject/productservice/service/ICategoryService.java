package com.internproject.productservice.service;

import com.internproject.productservice.dto.CategoryGetAllResponse;
import com.internproject.productservice.dto.UpdateCategoryRequest;
import com.internproject.productservice.entity.Category;

import java.util.List;

public interface ICategoryService {
    public void saveCategory(String categoryName);
    public List<CategoryGetAllResponse> getAllCategory();

    public boolean updateCategory(UpdateCategoryRequest categoryRequest);
}
