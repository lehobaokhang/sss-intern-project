package com.internproject.orderservice.mapper;

import com.internproject.orderservice.entity.Cart;
import com.internproject.orderservice.entity.Order;
import com.internproject.orderservice.entity.OrderProduct;

import java.util.ArrayList;
import java.util.List;

public class OrderMapper {
    private static OrderMapper INSTANCE;

    public static OrderMapper getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new OrderMapper();
        }
        return INSTANCE;
    }

    public Order toEntity(List<Cart> carts, String userId) {
        Order order = new Order();
        List<OrderProduct> orderProducts = new ArrayList<>();

        order.setUserId(userId);
        for (Cart cart : carts) {
            orderProducts.add(new OrderProduct(cart.getProductId()));
        }

        order.setOderProducts(orderProducts);

        return order;
    }
}
