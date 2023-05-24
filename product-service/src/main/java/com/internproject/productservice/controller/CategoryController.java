package com.internproject.productservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.internproject.productservice.dto.response.CategoryGetAllResponse;
import com.internproject.productservice.dto.request.UpdateCategoryRequest;
import com.internproject.productservice.service.CategoryService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/category")
public class CategoryController {
    private static final Logger logger = LogManager.getLogger(CategoryController.class);
    private CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> saveCategory(@RequestBody String request) {


        categoryService.saveCategory(bodyMap.get("categoryName"));
        logger.info("Add category to database");
        return ResponseEntity.ok("Category is created successfully");
    }

    @GetMapping
    public ResponseEntity<List<CategoryGetAllResponse>> getAllCategory() {
        List<CategoryGetAllResponse> categories = categoryService.getAllCategory();
        return ResponseEntity.ok(categories);
    }

    @PutMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> updateCategory(@RequestBody UpdateCategoryRequest request) {
        boolean isSuccess = categoryService.updateCategory(request);
        return isSuccess
                ? ResponseEntity.ok("Update category successfully")
                : ResponseEntity.badRequest().body("Cannot update category");

    }
}
