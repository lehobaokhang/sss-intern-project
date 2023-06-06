package com.internproject.orderservice.service;

import com.internproject.orderservice.config.JwtUtils;
import com.internproject.orderservice.dto.*;
import com.internproject.orderservice.entity.Cart;
import com.internproject.orderservice.entity.Order;
import com.internproject.orderservice.enumeration.ShipStatusEnum;
import com.internproject.orderservice.exception.CartException;
import com.internproject.orderservice.exception.OrderException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class Facade {
    private JwtUtils jwtUtils;
    private CartService cartService;
    private ProductService productService;
    private OrderService orderService;
    private MessageSender messageSender;

    @Autowired
    public Facade(JwtUtils jwtUtils,
                  CartService cartService,
                  ProductService productService,
                  OrderService orderService,
                  MessageSender messageSender) {
        this.jwtUtils = jwtUtils;
        this.cartService = cartService;
        this.productService = productService;
        this.orderService = orderService;
        this.messageSender = messageSender;
    }

    private String getIdFromBearerToken(String authorizationHeader) {
        String id = jwtUtils.getIdFromJwtToken(authorizationHeader);
        return id;
    }
    // Cart Facade
    public Cart addCart(CartDTO cartDTO,
                        String authorizationHeader) {
        String userId = getIdFromBearerToken(authorizationHeader);
        ProductDTO productDTO = productService.getProduct(cartDTO.getProductId(), authorizationHeader);
        if (productDTO.getSellerId().equals(userId)) {
            throw new CartException("Can not add product of yourself to your cart");
        }
        Cart cart = cartService.addCart(cartDTO, userId, productDTO);
        return cart;
    }

    public List<CartDTO> getAllCart(String authorizationHeader) {
        String userId = getIdFromBearerToken(authorizationHeader);
        List<CartDTO> carts = cartService.getAll(userId);
        List<String> productIds = carts.stream().map(cart -> cart.getProductId()).collect(Collectors.toList());
        List<ProductDTO> productDTOList = productService.getProductByIds(productIds, authorizationHeader);

        for (int i = 0; i < carts.size(); i++) {
            ProductDTO currentProduct = productDTOList.get(i);
            carts.get(i).setProductName(currentProduct.getProductName());
            carts.get(i).setProductImage(currentProduct.getProductImage());
        }
        return carts;
    }

    public void deleteCart(String id, String authorizationHeader) {
        String userId = getIdFromBearerToken(authorizationHeader);
        cartService.deleteCart(id, userId);
    }

    public void updateCart(String id, CartDTO cartDTO, String authorizationHeader) {
        String userId = getIdFromBearerToken(authorizationHeader);
        cartService.updateCart(id, cartDTO, userId);
    }

    // Order Facade
    public void addOrder(List<String> cartIds, String authorizationHeader) {
        String userId = getIdFromBearerToken(authorizationHeader);
        List<Cart> carts = cartService.getByIds(cartIds);
        if (carts.size() == 0) {
            throw new OrderException("Have one product does not in your cart");
        }
        List<String> cartUserId = carts.stream()
                .map(Cart::getUserId)
                .distinct()
                .collect(Collectors.toList());
        if (cartUserId.size() != 1 || !cartUserId.get(0).equals(userId)) {
            throw new OrderException("Have one product does not in your cart");
        }

        List<String> productIds = carts.stream().map(Cart::getProductId).collect(Collectors.toList());
        List<ProductDTO> products = productService.getProductByIds(productIds, authorizationHeader);
        List<Order> orders = orderService.saveOrder(carts, products);
        Map<String, Integer> quantityDecrease = carts.stream().collect(Collectors.toMap(Cart::getProductId, Cart::getQuantity));
        cartService.deleteAllByIds(cartIds);
        productService.decreaseQuantity(quantityDecrease, authorizationHeader);

        List<ShipDTO> ships =
            orders.stream().map(order -> ShipDTO.builder().orderId(order.getId())
                    .status(ShipStatusEnum.SHIPPING.getStatus()).build()).collect(Collectors.toList());
        messageSender.sendOrderMessage(ships);
    }

    public List<OrderDTO> getAllOrder(String authorizationHeader) {
        String userId = getIdFromBearerToken(authorizationHeader);
        List<OrderDTO> orders = orderService.getAll(userId);
        return orders;
    }

    public OrderDTO getOrderById(String id, String authorizationHeader) {
        String userId = getIdFromBearerToken(authorizationHeader);
        OrderDTO order = orderService.getOrderById(id, userId);
        return order;
    }

    public String getOrderByProductID(String id, String authorizationHeader) {
        String userId = getIdFromBearerToken(authorizationHeader);
        String orderId = orderService.getOrderByProductID(id, userId);
        return orderId;
    }
}
