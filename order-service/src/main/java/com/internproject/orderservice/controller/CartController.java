package com.internproject.orderservice.controller;

import com.internproject.orderservice.dto.CartDTO;
import com.internproject.orderservice.service.Facade;
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
    private Facade facade;

    @Autowired
    public CartController(Facade facade) {
        this.facade = facade;
    }

    @PostMapping
    @ApiOperation(value = "Add and update product to cart")
    public ResponseEntity<String> addCart(@RequestBody CartDTO cartDTO,
                                          @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader) {
        facade.addCart(cartDTO, authorizationHeader);
        return ResponseEntity.ok("Add product to cart successfully");
    }

    @GetMapping
    @ApiOperation(value = "Get all product in cart")
    public ResponseEntity<List<CartDTO>> getAll(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader) {
        List<CartDTO> carts = facade.getAll(authorizationHeader);
        return ResponseEntity.ok(carts);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete cart")
    public ResponseEntity<String> deleteCart(@PathVariable String id,
                                             @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader) {
        facade.deleteCart(id, authorizationHeader);
        return ResponseEntity.ok("Delete cart successfully");
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Update cart")
    public ResponseEntity<String> updateCart(@RequestBody CartDTO cartDTO,
                                             @PathVariable String id,
                                             @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader) {
        facade.updateCart(id, cartDTO, authorizationHeader);
        return ResponseEntity.ok("Update Cart Successfully");
    }
}
