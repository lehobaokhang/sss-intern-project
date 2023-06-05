package com.internproject.orderservice.service;

import com.internproject.orderservice.dto.ProductDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@FeignClient(name = "PRODUCT-SERVICE")
public interface ProductService {
    @GetMapping("/product/{id}")
    ProductDTO getProduct(@PathVariable("id") String id,
                          @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader);

    @PostMapping("/product/get-all-by-id")
    List<ProductDTO> getProductByIds(@RequestBody List<String> request,
                                     @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader);

    @PostMapping("/product/decrease-quantity")
    void decreaseQuantity(@RequestBody Map<String, Integer> request,
                          @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader);
}
