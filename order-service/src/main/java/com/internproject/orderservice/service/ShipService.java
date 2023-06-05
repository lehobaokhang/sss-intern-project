package com.internproject.orderservice.service;

import com.internproject.orderservice.dto.ShipDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

@FeignClient(name = "SHIP-SERVICE")
public interface ShipService {
    @PostMapping("/ship")
    void createShip(@RequestBody List<ShipDTO> request,
                    @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeaders);
}
