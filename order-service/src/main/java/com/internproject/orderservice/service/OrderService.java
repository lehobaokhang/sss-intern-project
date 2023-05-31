package com.internproject.orderservice.service;

import com.internproject.orderservice.dto.IdsRequest;
import com.internproject.orderservice.dto.OrderDTO;
import com.internproject.orderservice.dto.product.ProductDTO;
import com.internproject.orderservice.entity.Cart;
import com.internproject.orderservice.entity.Order;
import com.internproject.orderservice.entity.OrderProduct;
import com.internproject.orderservice.exception.OrderException;
import com.internproject.orderservice.exception.OrderNotFoundException;
import com.internproject.orderservice.mapper.OrderMapstruct;
import com.internproject.orderservice.repository.ICartRepository;
import com.internproject.orderservice.repository.IOrderRepository;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class OrderService {
    private ProductService productService;
    private IOrderRepository orderRepository;
    private ICartRepository cartRepository;
    private OrderMapstruct orderMapstruct;

    @Autowired
    public OrderService(ProductService productService,
                        IOrderRepository orderRepository,
                        ICartRepository cartRepository,
                        OrderMapstruct orderMapstruct) {
        this.productService = productService;
        this.orderRepository = orderRepository;
        this.cartRepository = cartRepository;
        this.orderMapstruct = orderMapstruct;
    }

    public List<Order> saveOrder(List<String> cartIds, String userId) {

        List<String> productIds = new ArrayList<>();
        List<OrderProduct> orderProducts = new ArrayList<>();
        int totalPrice = 0;
        for (Cart cart : carts) {
            if (!cart.getUserId().equals(userId)) {
                throw new OrderException(String.format("Have one product does not in your cart. productId: %s", cart.getProductId()));
            }
            productIds.add(cart.getProductId());
        }

        List<ProductDTO> products = productService.getProductByIds(productIds);
        Map<String, Order> orderMap = new HashMap<>();
        Map<String, Integer> quantityDecrease = new HashMap<>();
        for (int i = 0 ; i < carts.size() ; i++) {
            if (products.get(i).getQuantity() < carts.get(i).getQuantity()) {
                throw new OrderException("One product in your cart have a larger quantity than the remaining stock");
            }
            if (!orderMap.containsKey(products.get(i).getSellerId())) {
                Order orderTemp = new Order();
                orderTemp.setShippingFee(0);
                orderTemp.setUserId(userId);
                orderTemp.setPriceTotal(0);
                orderMap.put(products.get(i).getSellerId(), orderTemp);
            }
            ProductDTO productKey = products.get(i);
            Cart cartCurrent = carts.get(i);
            orderMap.get(productKey.getSellerId()).setPriceTotal(orderMap.get(productKey.getSellerId()).getPriceTotal() + productKey.getPrice());
            orderMap.get(productKey.getSellerId()).addOrderProduct(new OrderProduct(cartCurrent.getProductId(), cartCurrent.getQuantity(), cartCurrent.getPrice()));
            quantityDecrease.put(productKey.getId(), cartCurrent.getQuantity());
        }
        List<Order> orders = new ArrayList<>(orderMap.values());
        orderRepository.saveAll(orders);
        cartRepository.deleteAllById(idsRequest.getId());
        productService.decreaseQuantity(quantityDecrease);
        return orders;
    }

    public List<OrderDTO> getAll(String id) {
        List<Order> orders = orderRepository.findByUserId(id);
        List<OrderDTO> orderDTOS = orders.stream().map(order -> orderMapstruct.toOrderDTO(order)).collect(Collectors.toList());
        return orderDTOS;
    }

    public OrderDTO getOrderById(String id, String userid) {
        Optional<Order> orderOptional = orderRepository.findById(id);
        if (!orderOptional.isPresent()) {
            throw new OrderNotFoundException(String.format("Can not find any order with id: %s", id));
        }
        Order order = orderOptional.get();
        if (!userid.equals(order.getUserId())) {
            throw new OrderException("Can not get order of another user");
        }
        OrderDTO orderDTO = orderMapstruct.toOrderDTO(order);
        return orderDTO;
    }

    public String getOrderByProductID(String id, String userId) {
        Optional<Order> orderOptional = orderRepository.findByUserIdAndProductId(userId, id);
        if (!orderOptional.isPresent()) {
            throw new OrderNotFoundException(String.format("Can not find any order"));
        }
        return orderOptional.get().getId();
    }
}
