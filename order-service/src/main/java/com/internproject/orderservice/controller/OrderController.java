package com.internproject.orderservice.controller;

import com.internproject.orderservice.config.JwtUtils;
import com.internproject.orderservice.dto.order.OrderDTO;
import com.internproject.orderservice.service.IOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
@Api(value = "Order Controller", description = "Order Controller")
public class OrderController {
    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private IOrderService orderService;

    private String getIdFromBearerToken(String authorizationHeader) {
        String jwt = authorizationHeader.substring(7, authorizationHeader.length());
        String id = jwtUtils.getIdFromJwtToken(jwt);
        return id;
    }

    @PostMapping
    @ApiOperation(value = "Create new order")
    public ResponseEntity<?> saveOrder(OrderDTO orderDTO, @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader) {
        orderService.saveOrder(orderDTO, getIdFromBearerToken(authorizationHeader));
        return null;
    }
}
