package com.internproject.productservice.service;

import com.internproject.productservice.fallback.UserServiceFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "USER-SERVICE", fallback = UserServiceFallback.class)
public interface UserService {
    @GetMapping("/user/get-fullname/{id}")
    String getUserFullName(@PathVariable("id") String id);
}
