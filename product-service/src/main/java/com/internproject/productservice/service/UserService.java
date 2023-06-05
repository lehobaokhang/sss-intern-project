package com.internproject.productservice.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "USER-SERVICE")
public interface UserService {
    @GetMapping("/user/get-fullname/{id}")
    String getUserFullName(@PathVariable("id") String id,
                           @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader);
}
