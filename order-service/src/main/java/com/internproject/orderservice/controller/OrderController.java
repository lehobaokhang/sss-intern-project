package com.internproject.orderservice.controller;

import com.internproject.orderservice.dto.OrderDTO;
import com.internproject.orderservice.service.Facade;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
@Api(value = "Order Controller", description = "Order Controller")
public class OrderController {
    private Facade facade;

    @Autowired
    public OrderController(Facade facade) {
        this.facade = facade;
    }

    @PostMapping
    @ApiOperation(value = "Create new order")
    public ResponseEntity<String> addOrder(@RequestBody List<String> cartIds,
                                           @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader) {
        facade.addOrder(cartIds, authorizationHeader);
        return ResponseEntity.ok("Create order successfully");
    }

    @GetMapping
    @ApiOperation(value = "Get all order")
    public ResponseEntity<List<OrderDTO>> getAllOrder(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader) {
        List<OrderDTO> orders = facade.getAllOrder(authorizationHeader);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get order by order id")
    public ResponseEntity<OrderDTO> getOrderById(@PathVariable String id,
                                                 @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader) {
        OrderDTO order = facade.getOrderById(id, authorizationHeader);
        return ResponseEntity.ok(order);
    }

    @GetMapping("/get-order-by-productid/{id}")
    @ApiOperation(value = "Ship service get order id to check before customer rating")
    public ResponseEntity<String> getOrderByProductID(@PathVariable String id,
                                                      @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader) {
        String orderId = facade.getOrderByProductID(id, authorizationHeader);
        return ResponseEntity.ok(orderId);
    }
}
