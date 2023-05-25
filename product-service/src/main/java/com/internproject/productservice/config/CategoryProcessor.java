package com.internproject.productservice.config;

import com.internproject.productservice.entity.Category;
import org.springframework.batch.item.ItemProcessor;

public class CategoryProcessor implements ItemProcessor<Category, Category> {
    @Override
    public Category process(Category category) throws Exception {
        return category;
    }
}
