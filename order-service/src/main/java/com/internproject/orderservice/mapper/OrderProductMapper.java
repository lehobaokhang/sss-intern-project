package com.internproject.orderservice.mapper;

import com.internproject.orderservice.dto.order.OrderProductResponse;
import com.internproject.orderservice.entity.Cart;
import com.internproject.orderservice.entity.OrderProduct;

public class OrderProductMapper {
    private static OrderProductMapper INSTANCE;

    public static OrderProductMapper getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new OrderProductMapper();
        }
        return INSTANCE;
    }

    public OrderProduct toEntity(Cart cart) {
        OrderProduct orderProduct = new OrderProduct();
        orderProduct.setProductId(cart.getProductId());
        orderProduct.setPrice(cart.getPrice());
        orderProduct.setQuantity(cart.getQuantity());
        return orderProduct;
    }

    public OrderProductResponse toDTO(OrderProduct orderProduct) {
        OrderProductResponse orderProductResponse = new OrderProductResponse();
        orderProductResponse.setPrice(orderProduct.getPrice());
        orderProductResponse.setProductImage(orderProductResponse.getProductImage());
        orderProductResponse.setProductName(orderProductResponse.getProductName());
        orderProductResponse.setQuantity(orderProduct.getQuantity());
        return orderProductResponse;
    }
}
