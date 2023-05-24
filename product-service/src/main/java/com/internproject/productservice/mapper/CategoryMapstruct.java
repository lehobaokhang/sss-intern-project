package com.internproject.productservice.mapper;

import com.internproject.productservice.dto.CategoryDTO;
import com.internproject.productservice.entity.Category;
import org.mapstruct.Mapper;

@Mapper
public interface CategoryMapstruct {
    CategoryDTO toCategoryDTO(Category category);
    Category toCategory(CategoryDTO categoryDTO);
}
