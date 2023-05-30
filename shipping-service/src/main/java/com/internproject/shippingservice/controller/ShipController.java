package com.internproject.shippingservice.controller;

import com.internproject.shippingservice.config.JwtUtils;
import com.internproject.shippingservice.dto.CreateShipRequest;
import com.internproject.shippingservice.dto.ShipDTO;
import com.internproject.shippingservice.repository.IShipRepository;
import com.internproject.shippingservice.service.ShipService;
import com.internproject.shippingservice.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ship")
@Api(value = "Ship Controller", description = "Ship Controller")
public class ShipController {
    private ShipService shipService;
    private JwtUtils jwtUtils;
    private UserService userService;

    @Autowired
    public ShipController(ShipService shipService,
                          JwtUtils jwtUtils,
                          UserService userService) {
        this.shipService = shipService;
        this.jwtUtils = jwtUtils;
        this.userService = userService;
    }

    @PostMapping
    @ApiOperation(value = "Create new ship label for order")
    public ResponseEntity<String> createShip(@RequestBody CreateShipRequest ships) {
        shipService.createShip(ships);
        return ResponseEntity.ok("Create new ship label successful");
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Update tracking. This feature just used by SHIPPER role")
    @PreAuthorize("hasRole('ROLE_SHIPPER')")
    public ResponseEntity<String> updateTracking(@PathVariable String id,
                                                 @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader) {
        String userId = getIdFromBearerToken(authorizationHeader);
        int districtId = userService.getDistrict(userId);
        shipService.updateTracking(id, districtId);
        return ResponseEntity.ok("Update tracking successful");
    }

    private String getIdFromBearerToken(String authorizationHeader) {
        String jwt = authorizationHeader.substring(7, authorizationHeader.length());
        String id = jwtUtils.getIdFromJwtToken(jwt);
        return id;
    }
}
