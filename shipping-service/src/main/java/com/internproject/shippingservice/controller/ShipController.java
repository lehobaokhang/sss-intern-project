package com.internproject.shippingservice.controller;

import com.internproject.shippingservice.dto.ShipDTO;
import com.internproject.shippingservice.service.Facade;
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
    private Facade facade;

    @Autowired
    public ShipController(Facade facade) {
        this.facade = facade;
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Update tracking. This feature just used by SHIPPER role")
    @PreAuthorize("hasRole('ROLE_SHIPPER')")
    public ResponseEntity<String> updateTracking(@PathVariable String id,
                                                 @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader) {
        facade.updateTracking(id, authorizationHeader);
        return ResponseEntity.ok("Update tracking successful");
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Check ship status and tracking")
    public ResponseEntity<ShipDTO> tracking(@PathVariable String id) {
        ShipDTO shipDTO = facade.trackingOrder(id);
        return ResponseEntity.ok(shipDTO);
    }

    @PutMapping("/complete/{id}")
    @ApiOperation(value = "When customer receive parcel, they will confirm that received")
    public ResponseEntity<String> completeShipping(@PathVariable String id,
                                                   @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader) {
        facade.completeShipping(id, authorizationHeader);
        return ResponseEntity.ok("Shipping complete");
    }

    @GetMapping("/district/is-valid")
    public ResponseEntity<Boolean> isDistrictValid(@RequestParam("district") int district,
                                                   @RequestParam("province") int province) {
        boolean result = facade.isDistrictValid(district, province);
        return ResponseEntity.ok(result);
    }
}
