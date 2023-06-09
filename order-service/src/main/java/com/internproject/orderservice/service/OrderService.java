package com.internproject.orderservice.service;

import com.internproject.orderservice.dto.OrderDTO;
import com.internproject.orderservice.dto.ProductDTO;
import com.internproject.orderservice.entity.Cart;
import com.internproject.orderservice.entity.Order;
import com.internproject.orderservice.entity.OrderProduct;
import com.internproject.orderservice.exception.OrderException;
import com.internproject.orderservice.exception.OrderNotFoundException;
import com.internproject.orderservice.mapper.OrderMapstruct;
import com.internproject.orderservice.repository.IOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class OrderService {
    private IOrderRepository orderRepository;
    private OrderMapstruct orderMapstruct;

    @Autowired
    public OrderService(IOrderRepository orderRepository,
                        OrderMapstruct orderMapstruct) {
        this.orderRepository = orderRepository;
        this.orderMapstruct = orderMapstruct;
    }

    public List<Order> saveOrder(List<Cart> carts, List<ProductDTO> products) {
        Map<String, Order> orderMap = new HashMap<>();
        for (int i = 0 ; i < carts.size() ; i++) {
            if (products.get(i).getQuantity() < carts.get(i).getQuantity()) {
                throw new OrderException("One product in your cart have a larger quantity than the remaining stock");
            }
            ProductDTO currentProduct = products.get(i);
            Cart currentCart = carts.get(i);
            if (!orderMap.containsKey(currentProduct.getSellerId())) {
                Order orderTemp = new Order();
                orderTemp.setShippingFee(0);
                orderTemp.setUserId(currentCart.getUserId());
                orderTemp.setPriceTotal(0);
                orderMap.put(products.get(i).getSellerId(), orderTemp);
            }
            orderMap.get(currentProduct
                    .getSellerId())
                    .setPriceTotal(
                            orderMap.get(currentProduct.getSellerId()).getPriceTotal() + currentProduct.getPrice() * currentCart.getQuantity());
            orderMap.get(currentProduct.getSellerId()).addOrderProduct(new OrderProduct(currentCart.getProductId(), currentCart.getQuantity(), currentCart.getPrice()));
        }
        List<Order> orders = new ArrayList<>(orderMap.values());
        orderRepository.saveAll(orders);
        return orders;
    }

    public List<OrderDTO> getAll(String id) {
        List<Order> orders = orderRepository.findByUserId(id);
        return orders.stream().map(order -> orderMapstruct.toOrderDTO(order)).collect(Collectors.toList());
    }

    public OrderDTO getOrderById(String id, String userId) {
        Optional<Order> orderOptional = orderRepository.findByIdAndUserId(id, userId);
        if (!orderOptional.isPresent()) {
            throw new OrderNotFoundException(String.format("Can not find any order with id: %s", id));
        }
        return orderMapstruct.toOrderDTO(orderOptional.get());
    }

    public String getOrderByProductID(String id, String userId) {
        Optional<Order> orderOptional = orderRepository.findByUserIdAndProductId(userId, id);
        if (!orderOptional.isPresent()) {
            throw new OrderNotFoundException("Can not find any order");
        }
        return orderOptional.get().getId();
    }
}
