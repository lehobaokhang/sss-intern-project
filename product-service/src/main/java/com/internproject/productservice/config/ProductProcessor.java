package com.internproject.productservice.config;

import com.internproject.productservice.dto.ProductCsv;
import com.internproject.productservice.entity.Category;
import com.internproject.productservice.entity.Product;
import com.internproject.productservice.exception.CategoryNotFoundException;
import com.internproject.productservice.repository.ICategoryRepository;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class ProductProcessor implements ItemProcessor<ProductCsv, Product> {
    @Autowired
    private ICategoryRepository categoryRepository;

    @Override
    public Product process(ProductCsv productCsv) throws Exception {
        Product product = new Product();
        Optional<Category> categoryOptional = categoryRepository.findByCategoryName(productCsv.getProductCategory());
        if (!categoryOptional.isPresent()) {
            throw new CategoryNotFoundException(String.format("Can not find any category with category's name: %s", productCsv.getProductCategory()));
        }
        product.setProductName(productCsv.getProductName());
        product.setProductSize("");
        product.setProductWeight(0);
        product.setSellerId(productCsv.getSellerId());
        product.setCategory(categoryOptional.get());
        product.setDeleted(false);
        product.setPrice(productCsv.getPrice());
        product.setQuantity(productCsv.getQuantity());
        return product;
    }
}
