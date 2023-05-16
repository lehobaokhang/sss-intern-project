package com.internproject.orderservice.controller;

import com.internproject.orderservice.config.JwtUtils;
import com.internproject.orderservice.dto.AddAndUpdateCartDTO;
import com.internproject.orderservice.dto.CartDTO;
import com.internproject.orderservice.entity.Cart;
import com.internproject.orderservice.service.ICartService;
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
    public ResponseEntity<String> addCart(@RequestBody AddAndUpdateCartDTO addToCartDTO, @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader) {
        Cart cart = cartService.addCart(addToCartDTO, getIdFromBearerToken(authorizationHeader));
        return cart != null
                ? ResponseEntity.ok("Add product to cart successfully")
                : ResponseEntity.badRequest().body("Can not add product to cart");
    }

    @GetMapping
    @ApiOperation(value = "Get all product in cart")
    public ResponseEntity<List<CartDTO>> getAllCart(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader) {
        List<CartDTO> carts = cartService.getAll(getIdFromBearerToken(authorizationHeader));

        return ResponseEntity.ok(carts);
    }

    @PutMapping
    @ApiOperation(value = "Update cart")
    public ResponseEntity<String> updateCart(@RequestBody AddAndUpdateCartDTO updateCartDTO, @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader) {
        Cart cart = cartService.updateCart(updateCartDTO, getIdFromBearerToken(authorizationHeader));

        return cart != null
                ? ResponseEntity.ok("Update cart successfully")
                : ResponseEntity.badRequest().body("Can not update product to cart");
    }
}
