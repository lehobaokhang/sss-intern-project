package com.internproject.productservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.internproject.productservice.dto.response.CategoryGetAllResponse;
import com.internproject.productservice.dto.request.UpdateCategoryRequest;
import com.internproject.productservice.entity.Category;
import com.internproject.productservice.repository.ICategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class CategoryService {
    private ICategoryRepository categoryRepository;

    @Autowired
    public CategoryService(ICategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public void saveCategory(String request) {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> bodyMap = null;
        try {
            bodyMap = objectMapper.readValue(request, new TypeReference<Map<String, String>>(){});
        } catch (JsonProcessingException e) {
            return;
        }
        Category category = new Category();
        category.setCategoryName(categoryName);

        categoryRepository.save(category);
    }

    public List<CategoryGetAllResponse> getAllCategory() {
//        List<Category> categories = categoryRepository.findAll();
//        return categories.stream().map(category -> CategoryMapper.getInstance().toDTO(category)).collect(Collectors.toList());
        return null;
    }

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
