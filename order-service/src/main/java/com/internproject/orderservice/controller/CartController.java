package com.internproject.orderservice.controller;

import com.internproject.orderservice.config.JwtUtils;
import com.internproject.orderservice.dto.AddToCartDTO;
import com.internproject.orderservice.dto.ProductDTO;
import com.internproject.orderservice.entity.Cart;
import com.internproject.orderservice.service.ICartService;
import com.netflix.discovery.converters.Auto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
@Api(value = "Cart Controller", description = "Cart Controller")
public class CartController {
    @Autowired
    private ICartService cartService;

    @Autowired
    private JwtUtils jwtUtils;

    private String getIdFromBearerToken(String authorizationHeader) {
        String jwt = authorizationHeader.substring(7, authorizationHeader.length());
        String id = jwtUtils.getIdFromJwtToken(jwt);
        return id;
    }

    @PostMapping
    @ApiOperation(value = "Add product to cart")
    public ResponseEntity<String> addCart(@RequestBody AddToCartDTO addToCartDTO, @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader) {
        Cart cart = cartService.addCart(addToCartDTO, getIdFromBearerToken(authorizationHeader));
        return cart != null
                ? ResponseEntity.ok("success")
                : ResponseEntity.badRequest().body("Can not add product to cart");
    }
}
