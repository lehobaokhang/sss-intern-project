package com.internproject.orderservice.controller;

import com.internproject.orderservice.config.JwtUtils;
import com.internproject.orderservice.dto.CartDTO;
import com.internproject.orderservice.dto.cart.CartResponse;
import com.internproject.orderservice.service.CartService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
@Api(value = "Cart Controller", description = "Cart Controller")
public class CartController {
    private CartService cartService;
    private JwtUtils jwtUtils;

    @Autowired
    public CartController(CartService cartService, JwtUtils jwtUtils) {
        this.cartService = cartService;
        this.jwtUtils = jwtUtils;
    }

    private String getIdFromBearerToken(String authorizationHeader) {
        String jwt = authorizationHeader.substring(7, authorizationHeader.length());
        String id = jwtUtils.getIdFromJwtToken(jwt);
        return id;
    }

    @PostMapping
    @ApiOperation(value = "Add and update product to cart")
    public ResponseEntity<String> addCart(@RequestBody CartDTO cartDTO, @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader) {
        String userId = getIdFromBearerToken(authorizationHeader);
        cartService.addCart(cartDTO, userId);
        return ResponseEntity.ok("Add product to cart successfully");
    }

    @GetMapping
    @ApiOperation(value = "Get all product in cart")
    public ResponseEntity<List<CartResponse>> getAllCart(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader) {
        List<CartResponse> carts = cartService.getAll(getIdFromBearerToken(authorizationHeader));
        return ResponseEntity.ok(carts);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete cart")
    public ResponseEntity<String> deleteCart(@PathVariable String id,
                                             @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader) {
        String userId = getIdFromBearerToken(authorizationHeader);
        cartService.deleteCart(id, userId);
        return ResponseEntity.ok("Delete cart successfully");
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Update cart")
    public ResponseEntity<String> updateCart(@RequestBody CartDTO cartDTO,
                                             @PathVariable String id,
                                             @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader) {
        String userId = getIdFromBearerToken(authorizationHeader);
        cartService.updateCart(id, cartDTO, userId);
        return ResponseEntity.ok("Update Cart Successfully");
    }
}
