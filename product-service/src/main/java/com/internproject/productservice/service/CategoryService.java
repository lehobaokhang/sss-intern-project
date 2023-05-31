package com.internproject.productservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.internproject.productservice.dto.CategoryDTO;
import com.internproject.productservice.entity.Category;
import com.internproject.productservice.exception.CategoryNotFoundException;
import com.internproject.productservice.mapper.CategoryMapstruct;
import com.internproject.productservice.repository.ICategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryService {
    private ICategoryRepository categoryRepository;
    private CategoryMapstruct categoryMapstruct;

    @Autowired
    public CategoryService(ICategoryRepository categoryRepository,
                           CategoryMapstruct categoryMapstruct) {
        this.categoryRepository = categoryRepository;
        this.categoryMapstruct = categoryMapstruct;
    }

    public Category getCategoryByCategoryName(String categoryName) {
        Optional<Category> categoryOptional = categoryRepository.findByCategoryName(categoryName);
        if (!categoryOptional.isPresent()) {
            throw new CategoryNotFoundException(String.format("Can not find any category with name: %s", categoryName));
        }
        return categoryOptional.get();
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
        category.setCategoryName(bodyMap.get("categoryName"));
        categoryRepository.save(category);
    }

    public List<CategoryDTO> getAllCategory() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream()
                .map(category -> categoryMapstruct.toCategoryDTO(category))
                .collect(Collectors.toList());
    }

    public void updateCategory(String id, CategoryDTO categoryDTO) {
        Optional<Category> categoryOptional = categoryRepository.findById(id);
        if (categoryOptional.isPresent()) {
            Category category = categoryOptional.get();
            category.setCategoryName(categoryDTO.getCategoryName());
            categoryRepository.save(category);
        } else {
            throw new CategoryNotFoundException("Can not find any category with id: " + id);
        }
    }

    public void deleteCategory(String id) {
        categoryRepository.deleteById(id);
    }
}
