package com.internproject.productservice.service.impl;

import com.internproject.productservice.dto.CreateProductRequest;
import com.internproject.productservice.entity.Product;
import com.internproject.productservice.mapper.ProductMapper;
import com.internproject.productservice.repository.IProductRepository;
import com.internproject.productservice.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements IProductService {
    @Autowired
    private IProductRepository productRepository;

    @Override
    public void saveProduct(CreateProductRequest request, String id) {
        Product product = ProductMapper.getInstance().toProduct(request);
        product.setSellerId(id);
        productRepository.save(product);
    }
}
