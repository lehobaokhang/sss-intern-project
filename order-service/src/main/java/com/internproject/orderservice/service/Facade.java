package com.internproject.orderservice.service;

import com.internproject.orderservice.config.JwtUtils;
import com.internproject.orderservice.dto.CartDTO;
import com.internproject.orderservice.dto.product.ProductDTO;
import com.internproject.orderservice.entity.Cart;
import com.internproject.orderservice.entity.Order;
import com.internproject.orderservice.exception.CartException;
import com.internproject.orderservice.exception.OrderException;
import com.internproject.orderservice.exception.ProductNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
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

    public List<CartDTO> getAll(String authorizationHeader) {
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

        List<Order> orders = orderService.saveOrder(cartIds, userId);
    }
}
