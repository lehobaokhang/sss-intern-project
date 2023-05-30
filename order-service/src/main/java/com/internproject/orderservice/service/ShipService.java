package com.internproject.orderservice.service;

import com.internproject.orderservice.dto.CreateShipRequest;
import com.internproject.orderservice.dto.ShipDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "SHIP-SERVICE")
public interface ShipService {
    @PostMapping("/ship")
    void createShip(@RequestBody CreateShipRequest request);
}
