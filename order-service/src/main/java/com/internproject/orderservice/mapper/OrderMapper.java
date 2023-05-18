package com.internproject.orderservice.mapper;

import com.internproject.orderservice.dto.order.OrderProductResponse;
import com.internproject.orderservice.dto.order.OrderResponse;
import com.internproject.orderservice.entity.Cart;
import com.internproject.orderservice.entity.Order;
import com.internproject.orderservice.entity.OrderProduct;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class OrderMapper {
    private static OrderMapper INSTANCE;

    public static OrderMapper getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new OrderMapper();
        }
        return INSTANCE;
    }

    public OrderResponse toDTO(Order order) {
        List<OrderProductResponse> orderProducts = order.getOderProducts().stream()
                .map(orderElement -> OrderProductMapper.getInstance().toDTO(orderElement))
                .collect(Collectors.toList());
        OrderResponse orderResponse = new OrderResponse();
        orderResponse.setShippingFee(order.getShippingFee());
        orderResponse.setPriceTotal(order.getPriceTotal());
        orderResponse.setCreatedAt(order.getCreatedAt());
        orderResponse.setId(order.getId());
        orderResponse.setOrderProducts(orderProducts);
        return orderResponse;
    }
}
