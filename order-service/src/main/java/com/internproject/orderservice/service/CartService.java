package com.internproject.orderservice.service;

import com.internproject.orderservice.dto.CartDTO;
import com.internproject.orderservice.dto.product.ProductDTO;
import com.internproject.orderservice.entity.Cart;
import com.internproject.orderservice.exception.CartException;
import com.internproject.orderservice.exception.CartNotFoundException;
import com.internproject.orderservice.mapper.CartMapstruct;
import com.internproject.orderservice.repository.ICartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CartService {
    private ProductService productService;
    private ICartRepository cartRepository;
    private CartMapstruct cartMapstruct;

    @Autowired
    public CartService(ProductService productService, ICartRepository cartRepository, CartMapstruct cartMapstruct) {
        this.productService = productService;
        this.cartRepository = cartRepository;
        this.cartMapstruct = cartMapstruct;
    }

    public Cart addCart(CartDTO cartDTO, String userId, ProductDTO productDTO) {
        Cart cart = cartMapstruct.toCart(cartDTO);
        cart.setUserId(userId);
        cart.setPrice(productDTO.getPrice());
        try {
            cartRepository.save(cart);
        } catch (Exception e) {
            throw new CartException("This product in your cart yet");
        }
        return cart;
    }

    public List<CartDTO> getAll(String userId) {
        List<Cart> carts = cartRepository.findByUserId(userId);
        List<CartDTO> cartsReturn = carts.stream().map(cart -> cartMapstruct.toCartDTO(cart)).collect(Collectors.toList());
        return cartsReturn;
    }

    @Transactional
    public void deleteCart(String id, String userId) {
        cartRepository.deleteByIdAndUserId(id, userId);
    }

    public Cart updateCart(String id, CartDTO cartDTO, String userId) {
        Optional<Cart> cartOptional = cartRepository.findById(id);
        if (!cartOptional.isPresent()) {
            throw new CartNotFoundException(String.format("Can not find any cart with id: %s", id));
        }
        Cart cart = cartOptional.get();
        if (!userId.equals(cart.getUserId())) {
            throw new CartException("Can not update cart of other user");
        }
        cart.setQuantity(cartDTO.getQuantity());
        cartRepository.save(cart);
        return cart;
    }

    public List<Cart> getByIds(List<String> cartIds) {
        return cartRepository.findAllById(cartIds);
    }

    public void deleteAllByIds(List<String> ids) {
        cartRepository.deleteAllById(ids);
    }
}
