package com.internproject.orderservice.service.impl;

import com.internproject.orderservice.dto.order.CreateOrderRequest;
import com.internproject.orderservice.dto.order.OrderProductResponse;
import com.internproject.orderservice.dto.order.OrderResponse;
import com.internproject.orderservice.dto.product.GetByIdsRequest;
import com.internproject.orderservice.dto.product.ProductDTO;
import com.internproject.orderservice.entity.Cart;
import com.internproject.orderservice.entity.Order;
import com.internproject.orderservice.entity.OrderProduct;
import com.internproject.orderservice.mapper.OrderMapper;
import com.internproject.orderservice.mapper.OrderProductMapper;
import com.internproject.orderservice.repository.ICartRepository;
import com.internproject.orderservice.repository.IOrderRepository;
import com.internproject.orderservice.service.IOrderService;
import com.internproject.orderservice.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements IOrderService {
    @Autowired
    private IProductService productService;

    @Autowired
    private IOrderRepository orderRepository;

    @Autowired
    private ICartRepository cartRepository;

    @Override
    public Order saveOrder(CreateOrderRequest orderDTO, String userId) {
        Order order = new Order();
        int total = 0;
        List<OrderProduct> orderProducts = new ArrayList<>();

        try {
            List<Cart> carts = cartRepository.findAllById(orderDTO.getCartId());

            for (Cart cart : carts) {
                orderProducts.add(OrderProductMapper.getInstance().toEntity(cart));
                ProductDTO productDTO = productService.getProduct(cart.getProductId());
//                orderProducts.set
            }

            order.setUserId(userId);
            order.setOderProducts(orderProducts);
            order.setShippingFee(0);
            order.setPriceTotal(total);
            orderRepository.save(order);
            cartRepository.deleteAllById(orderDTO.getCartId());
        } catch (Exception e) {
            return null;
        }
        return order;
    }

    @Override
    public List<OrderResponse> getAll(String id) {
        List<Order> orders = orderRepository.findByUserId(id);

        List<OrderResponse> orderResponses = new ArrayList<>();
        for (Order order : orders) {
            List<String> ids = order.getOderProducts().stream().map(orderProduct -> orderProduct.getProductId()).collect(Collectors.toList());
            List<ProductDTO> products = productService.getProductByIds(new GetByIdsRequest(ids));
//            List<OrderProductResponse> orderProductResponses = products.stream().map(productDTO -> )
        }

        return orderResponses;
    }
}
