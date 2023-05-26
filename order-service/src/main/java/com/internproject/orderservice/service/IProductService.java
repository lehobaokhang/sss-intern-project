package com.internproject.orderservice.service;

import com.internproject.orderservice.dto.IdsRequest;
import com.internproject.orderservice.dto.product.ProductDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "PRODUCT-SERVICE")
public interface IProductService {
    @GetMapping("/product/{id}")
    ProductDTO getProduct(@PathVariable("id") String id);

    @GetMapping("/product/get-quantity/{id}")
    int getQuantity(@PathVariable("id") String id);

    @PostMapping("/product/get-all-by-id")
    List<ProductDTO> getProductByIds(@RequestBody IdsRequest request);
}
