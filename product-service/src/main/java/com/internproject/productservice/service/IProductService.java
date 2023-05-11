package com.internproject.productservice.service;

import com.internproject.productservice.dto.CreateProductRequest;

public interface IProductService {
    public void saveProduct(CreateProductRequest request, String id);
}
