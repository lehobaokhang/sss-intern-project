//package com.internproject.orderservice.controller;
//
//import com.internproject.orderservice.config.JwtUtils;
//import com.internproject.orderservice.dto.order.CreateOrderRequest;
//import com.internproject.orderservice.dto.order.OrderResponse;
//import com.internproject.orderservice.entity.Order;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/order")
//@Api(value = "Order Controller", description = "Order Controller")
//public class OrderController {
//    @Autowired
//    private JwtUtils jwtUtils;
//
//    @Autowired
//    private IOrderService orderService;
//
//    private String getIdFromBearerToken(String authorizationHeader) {
//        String jwt = authorizationHeader.substring(7, authorizationHeader.length());
//        String id = jwtUtils.getIdFromJwtToken(jwt);
//        return id;
//    }
//
//    @PostMapping
//    @ApiOperation(value = "Create new order")
//    public ResponseEntity<String> saveOrder(@RequestBody CreateOrderRequest orderDTO, @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader) {
//        Order order = orderService.saveOrder(orderDTO, getIdFromBearerToken(authorizationHeader));
//        return (order != null)
//                ? ResponseEntity.ok("Create order successfully")
//                : ResponseEntity.status(400).body("Something went wrong");
//    }
//
//    @GetMapping
//    @ApiOperation(value = "Get all order")
//    public ResponseEntity<List<OrderResponse>> getAllOrder(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader) {
//        List<OrderResponse> orders = orderService.getAll(getIdFromBearerToken(authorizationHeader));
//        return ResponseEntity.ok(orders);
//    }
//}
