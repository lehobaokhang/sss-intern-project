package com.internproject.productservice.service;

import com.internproject.productservice.dto.response.CategoryGetAllResponse;
import com.internproject.productservice.dto.request.UpdateCategoryRequest;

import java.util.List;

public interface ICategoryService {
    public void saveCategory(String categoryName);
    public List<CategoryGetAllResponse> getAllCategory();

    public boolean updateCategory(UpdateCategoryRequest categoryRequest);
}
