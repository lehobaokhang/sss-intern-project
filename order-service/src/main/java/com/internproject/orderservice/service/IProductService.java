package com.internproject.orderservice.service;

import com.internproject.orderservice.dto.ProductDTO;
import com.internproject.orderservice.fallback.ProductServiceFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "PRODUCT-SERVICE", fallback = ProductServiceFallback.class)
public interface IProductService {
    @GetMapping("/product/{id}")
    ProductDTO getProduct(@PathVariable("id") String id);
}
