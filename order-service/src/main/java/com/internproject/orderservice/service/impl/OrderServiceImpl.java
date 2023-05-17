package com.internproject.orderservice.service.impl;

import com.internproject.orderservice.dto.order.CartResponse;
import com.internproject.orderservice.dto.order.OrderDTO;
import com.internproject.orderservice.entity.Cart;
import com.internproject.orderservice.entity.Order;
import com.internproject.orderservice.mapper.OrderMapper;
import com.internproject.orderservice.repository.IOrderRepository;
import com.internproject.orderservice.service.ICartService;
import com.internproject.orderservice.service.IOrderService;
import com.internproject.orderservice.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements IOrderService {
    @Autowired
    private ICartService cartService;

    @Autowired
    private IProductService productService;

    @Autowired
    private IOrderRepository orderRepository;

    @Override
    public CartResponse saveOrder(OrderDTO orderDTO, String userId) {
        List<Cart> carts = orderDTO.getCartId().stream()
                .map(cart -> cartService.getCartById(cart))
                .collect(Collectors.toList());

        Order order = OrderMapper.getInstance().toEntity(carts, userId);

//        int total = carts.stream().map(cart -> productService.getProduct(cart.getProductId()).get)

        return null;
    }
}
