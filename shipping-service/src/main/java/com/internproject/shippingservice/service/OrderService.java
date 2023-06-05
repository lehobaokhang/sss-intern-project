package com.internproject.shippingservice.service;

import com.internproject.shippingservice.config.FeignClientConfig;
import com.internproject.shippingservice.dto.OrderDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "ORDER-SERVICE", configuration = FeignClientConfig.class)
public interface OrderService {
    @GetMapping("/order/{id}")
    OrderDTO getOrder(@PathVariable("id") String id, @RequestHeader(HttpHeaders.AUTHORIZATION) String token);

    @GetMapping("/order/get-order-by-productid/{id}")
    String getOrderByProductId(@PathVariable("id") String id, @RequestHeader(HttpHeaders.AUTHORIZATION) String token);
}
