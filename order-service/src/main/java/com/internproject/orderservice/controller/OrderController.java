package com.internproject.orderservice.controller;

import com.internproject.orderservice.config.JwtUtils;
import com.internproject.orderservice.dto.CreateShipRequest;
import com.internproject.orderservice.dto.IdsRequest;
import com.internproject.orderservice.dto.OrderDTO;
import com.internproject.orderservice.dto.ShipDTO;
import com.internproject.orderservice.entity.Order;
import com.internproject.orderservice.service.Facade;
import com.internproject.orderservice.service.OrderService;
import com.internproject.orderservice.service.ShipService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/order")
@Api(value = "Order Controller", description = "Order Controller")
public class OrderController {
    private JwtUtils jwtUtils;
    private OrderService orderService;
    private ShipService shipService;
    private Facade facade;

    @Autowired
    public OrderController(JwtUtils jwtUtils,
                           OrderService orderService,
                           ShipService shipService,
                           Facade facade) {
        this.jwtUtils = jwtUtils;
        this.orderService = orderService;
        this.shipService = shipService;
        this.facade = facade;
    }

    private String getIdFromBearerToken(String authorizationHeader) {
        String jwt = authorizationHeader.substring(7, authorizationHeader.length());
        String id = jwtUtils.getIdFromJwtToken(jwt);
        return id;
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
