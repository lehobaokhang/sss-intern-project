package com.internproject.orderservice.fallback;

import com.internproject.orderservice.dto.product.ProductDTO;
import com.internproject.orderservice.service.IProductService;
import org.springframework.stereotype.Component;

@Component
public class ProductServiceFallback implements IProductService {
    @Override
    public ProductDTO getProduct(String id) {
        return null;
    }
}
