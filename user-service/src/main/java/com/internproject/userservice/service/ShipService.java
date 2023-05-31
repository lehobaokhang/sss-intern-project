package com.internproject.userservice.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "SHIP-SERVICE")
public interface ShipService {
    @GetMapping("/ship/district/is-valid")
    boolean isDistrictValid(@RequestParam("district") int district, @RequestParam("province") int province);
}
