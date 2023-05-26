package com.internproject.orderservice.controller;

import com.internproject.orderservice.config.JwtUtils;
import com.internproject.orderservice.dto.IdsRequest;
import com.internproject.orderservice.dto.OrderDTO;
import com.internproject.orderservice.entity.Order;
import com.internproject.orderservice.service.OrderService;
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
    private JwtUtils jwtUtils;
    private OrderService orderService;

    @Autowired
    public OrderController(JwtUtils jwtUtils, OrderService orderService) {
        this.jwtUtils = jwtUtils;
        this.orderService = orderService;
    }

    private String getIdFromBearerToken(String authorizationHeader) {
        String jwt = authorizationHeader.substring(7, authorizationHeader.length());
        String id = jwtUtils.getIdFromJwtToken(jwt);
        return id;
    }

    @PostMapping
    @ApiOperation(value = "Create new order")
    public ResponseEntity<String> saveOrder(@RequestBody IdsRequest idsRequest,
                                            @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader) {
        Order order = orderService.saveOrder(idsRequest, getIdFromBearerToken(authorizationHeader));
        return ResponseEntity.ok("Create order successfully");
    }

    @GetMapping
    @ApiOperation(value = "Get all order")
    public ResponseEntity<List<OrderDTO>> getAllOrder(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader) {
        List<OrderDTO> orders = orderService.getAll(getIdFromBearerToken(authorizationHeader));
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get order by order id")
    public ResponseEntity<OrderDTO> getOrderById(@PathVariable String id,
                                                 @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader) {
        OrderDTO order = orderService.getOrderById(id, getIdFromBearerToken(authorizationHeader));
        return ResponseEntity.ok(order);
    }
}
