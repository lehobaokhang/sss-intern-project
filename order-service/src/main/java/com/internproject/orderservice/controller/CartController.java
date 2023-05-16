package com.internproject.orderservice.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cart")
@Api(value = "Cart Controller", description = "Cart Controller")
public class CartController {
    @PostMapping
    @ApiOperation(value = "Add product to cart")
    public ResponseEntity<String> addCart() {
        return ResponseEntity.ok("Product has been add to cart");
    }
}
