package com.internproject.userservice.service;

import com.internproject.userservice.fallback.ShipServiceFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "SHIP-SERVICE", fallback = ShipServiceFallback.class)
public interface ShipService {
    @GetMapping("/ship/district/is-valid")
    boolean isDistrictValid(@RequestParam("district") int district,
                            @RequestParam("province") int province,
                            @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader);
}
