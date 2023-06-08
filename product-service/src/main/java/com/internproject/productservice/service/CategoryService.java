package com.internproject.productservice.service;

import com.internproject.productservice.dto.CategoryDTO;
import com.internproject.productservice.entity.Category;
import com.internproject.productservice.exception.CategoryException;
import com.internproject.productservice.exception.CategoryNotFoundException;
import com.internproject.productservice.mapper.CategoryMapstruct;
import com.internproject.productservice.repository.ICategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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

    public void saveCategory(CategoryDTO categoryDTO) {
        if (categoryRepository.existsByCategoryName(categoryDTO.getCategoryName())) {
            throw new CategoryException("This category already exists");
        }
        Category category = categoryMapstruct.toCategory(categoryDTO);
        categoryRepository.save(category);
    }

    public List<CategoryDTO> getAllCategory() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream()
                .map(category -> categoryMapstruct.toCategoryDTO(category))
                .collect(Collectors.toList());
    }

    public void updateCategory(String id, CategoryDTO categoryDTO) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new CategoryNotFoundException("Can not find any category with id: " + id));
        category.setCategoryName(categoryDTO.getCategoryName());
        categoryRepository.save(category);
    }

    public void deleteCategory(String id) {
        categoryRepository.deleteById(id);
    }
}
