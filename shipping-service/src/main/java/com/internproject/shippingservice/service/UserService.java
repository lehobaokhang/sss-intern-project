package com.internproject.shippingservice.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "USER-SERVICE")
public interface UserService {
    @GetMapping("/user/get-district/{id}")
    int getDistrict(@PathVariable("id") String id,
                    @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeaders);
}
