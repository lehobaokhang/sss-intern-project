package com.internproject.productservice.config;

import com.internproject.productservice.mapper.CategoryMapstruct;
import com.internproject.productservice.mapper.CategoryMapstructImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class Config {
    @Bean
    public CategoryMapstruct categoryMapstruct() {
        return new CategoryMapstructImpl();
    }
}
