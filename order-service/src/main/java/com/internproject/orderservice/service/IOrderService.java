package com.internproject.orderservice.service;

import com.internproject.orderservice.dto.order.CreateOrderRequest;
import com.internproject.orderservice.dto.order.OrderResponse;
import com.internproject.orderservice.entity.Order;

import java.util.List;

public interface IOrderService {
    Order saveOrder(CreateOrderRequest orderDTO, String userId);
    List<OrderResponse> getAll(String id);
}
