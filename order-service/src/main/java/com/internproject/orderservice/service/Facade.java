package com.internproject.orderservice.service;

import com.internproject.orderservice.config.JwtUtils;
import com.internproject.orderservice.dto.CartDTO;
import com.internproject.orderservice.dto.OrderDTO;
import com.internproject.orderservice.dto.ShipDTO;
import com.internproject.orderservice.dto.ProductDTO;
import com.internproject.orderservice.entity.Cart;
import com.internproject.orderservice.entity.Order;
import com.internproject.orderservice.exception.CartException;
import com.internproject.orderservice.exception.OrderException;
import com.internproject.orderservice.exception.ProductNotFoundException;
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
    private ShipService shipService;
    private OrderService orderService;

    @Autowired
    public Facade(JwtUtils jwtUtils,
                  CartService cartService,
                  ProductService productService,
                  ShipService shipService,
                  OrderService orderService) {
        this.jwtUtils = jwtUtils;
        this.cartService = cartService;
        this.productService = productService;
        this.shipService = shipService;
        this.orderService = orderService;
    }

    private String getIdFromBearerToken(String authorizationHeader) {
        String jwt = authorizationHeader.substring(7, authorizationHeader.length());
        String id = jwtUtils.getIdFromJwtToken(jwt);
        return id;
    }
    // Cart Facade
    public void addCart(CartDTO cartDTO,
                        String authorizationHeader) {
        String userId = getIdFromBearerToken(authorizationHeader);
        ProductDTO productDTO = productService.getProduct(cartDTO.getProductId());
        if (productDTO == null) {
            throw new ProductNotFoundException(String.format("Can not find any product with id: %s", cartDTO.getProductId()));
        }
        if (productDTO.getSellerId().equals(userId)) {
            throw new CartException("Can not add product of yourself to your cart");
        }
        cartService.addCart(cartDTO, userId, productDTO);
    }

    public List<CartDTO> getAllCart(String authorizationHeader) {
        String userId = getIdFromBearerToken(authorizationHeader);
        List<CartDTO> carts = cartService.getAll(userId);
        List<String> productIds = carts.stream().map(cart -> cart.getProductId()).collect(Collectors.toList());
        List<ProductDTO> productDTOList = productService.getProductByIds(productIds);
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
        List<ProductDTO> products = productService.getProductByIds(productIds);
        List<Order> orders = orderService.saveOrder(carts, products);
        Map<String, Integer> quantityDecrease = carts.stream().collect(Collectors.toMap(Cart::getProductId, Cart::getQuantity));
        cartService.deleteAllByIds(cartIds);
        productService.decreaseQuantity(quantityDecrease);

        List<ShipDTO> ships =
            orders.stream().map(order -> ShipDTO.builder().orderId(order.getId()).status("SHIPPING").build()).collect(Collectors.toList());
        shipService.createShip(ships);
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
