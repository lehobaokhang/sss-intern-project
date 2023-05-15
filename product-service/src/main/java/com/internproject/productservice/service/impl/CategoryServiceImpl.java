package com.internproject.productservice.service.impl;

import com.internproject.productservice.dto.CategoryGetAllResponse;
import com.internproject.productservice.dto.UpdateCategoryRequest;
import com.internproject.productservice.entity.Category;
import com.internproject.productservice.mapper.CategoryMapper;
import com.internproject.productservice.repository.ICategoryRepository;
import com.internproject.productservice.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements ICategoryService {
    @Autowired
    private ICategoryRepository categoryRepository;

    @Override
    public void saveCategory(String categoryName) {
        Category category = new Category();
        category.setCategoryName(categoryName);

        categoryRepository.save(category);
    }

    @Override
    public List<CategoryGetAllResponse> getAllCategory() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream().map(category -> CategoryMapper.getInstance().toDTO(category)).collect(Collectors.toList());
    }

    @Override
    public boolean updateCategory(UpdateCategoryRequest categoryRequest) {
        Optional<Category> categoryOptional = categoryRepository.findById(categoryRequest.getId());

        if (categoryOptional.isPresent()) {
            Category category = categoryOptional.get();
            category.setCategoryName(categoryRequest.getCategoryName());
            categoryRepository.save(category);
            return true;
        } else {
            return false;
        }
    }
}
