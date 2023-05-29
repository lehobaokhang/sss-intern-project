package com.internproject.shippingservice.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "USER-SERVICE")
public interface UserService {
    @GetMapping("/user/get-district/{id}")
    int getDistrict(@PathVariable("id") String id);
}
