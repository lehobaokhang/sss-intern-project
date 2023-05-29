package com.internproject.orderservice.controller;

import com.internproject.orderservice.config.JwtUtils;
import com.internproject.orderservice.dto.IdsRequest;
import com.internproject.orderservice.dto.OrderDTO;
import com.internproject.orderservice.dto.ShipDTO;
import com.internproject.orderservice.dto.TrackingDTO;
import com.internproject.orderservice.entity.Order;
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

@RestController
@RequestMapping("/order")
@Api(value = "Order Controller", description = "Order Controller")
public class OrderController {
    private JwtUtils jwtUtils;
    private OrderService orderService;
    private ShipService shipService;

    @Autowired
    public OrderController(JwtUtils jwtUtils,
                           OrderService orderService,
                           ShipService shipService) {
        this.jwtUtils = jwtUtils;
        this.orderService = orderService;
        this.shipService = shipService;
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

        ShipDTO shipDTO = new ShipDTO();
        shipDTO.setOrderId(order.getId());
        shipDTO.setStatus("SHIPPING");
        List<TrackingDTO> tracking = new ArrayList<>();
        TrackingDTO trackingDTO = new TrackingDTO();

        tracking.add(new TrackingDTO());
        shipDTO.setTracking(tracking);
        shipService.createShip(shipDTO);
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
