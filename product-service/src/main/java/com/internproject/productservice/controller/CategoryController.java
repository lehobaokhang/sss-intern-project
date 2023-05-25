package com.internproject.productservice.controller;

import com.internproject.productservice.dto.CategoryDTO;
import com.internproject.productservice.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {
    private CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> saveCategory(@RequestBody String request) {
        categoryService.saveCategory(request);
        return ResponseEntity.ok("Category is created successfully");
    }

    @GetMapping
    public ResponseEntity<List<CategoryDTO>> getAllCategory() {
        List<CategoryDTO> categories = categoryService.getAllCategory();
        return ResponseEntity.ok(categories);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> updateCategory(@PathVariable("id") String id,
                                                 @RequestBody CategoryDTO categoryDTO) {
        categoryService.updateCategory(id, categoryDTO);
        return ResponseEntity.ok("Update category successfully");
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteCategory(@PathVariable("id") String id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.ok("Delete Category Successfully");
    }
}
