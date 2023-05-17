package com.internproject.orderservice.service;

import com.internproject.orderservice.dto.order.CartResponse;
import com.internproject.orderservice.dto.order.OrderDTO;

import java.util.List;

public interface IOrderService {
    public CartResponse saveOrder(OrderDTO orderDTO, String userId);
}
