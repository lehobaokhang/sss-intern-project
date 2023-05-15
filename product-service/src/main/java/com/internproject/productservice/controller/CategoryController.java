package com.internproject.productservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.internproject.productservice.dto.CategoryGetAllResponse;
import com.internproject.productservice.dto.UpdateCategoryRequest;
import com.internproject.productservice.entity.Category;
import com.internproject.productservice.service.ICategoryService;
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

    @Autowired
    private ICategoryService categoryService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> saveCategory(@RequestBody String request) {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> bodyMap = null;
        try {
            bodyMap = objectMapper.readValue(request, new TypeReference<Map<String, String>>(){});
        } catch (JsonProcessingException e) {
            logger.error(e.getMessage());
            return ResponseEntity.badRequest().body("Bad Request");
        }

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
