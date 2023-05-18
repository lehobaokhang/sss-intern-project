package com.internproject.orderservice.service.impl;

import com.internproject.orderservice.dto.cart.CartRequestDTO;
import com.internproject.orderservice.dto.cart.CartResponseDTO;
import com.internproject.orderservice.dto.product.ProductDTO;
import com.internproject.orderservice.entity.Cart;
import com.internproject.orderservice.mapper.CartMapper;
import com.internproject.orderservice.repository.ICartRepository;
import com.internproject.orderservice.service.ICartService;
import com.internproject.orderservice.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements ICartService {
    @Autowired
    private IProductService productService;

    @Autowired
    private ICartRepository cartRepository;

    @Override
    public Cart addCart(CartRequestDTO cartRequestDTO, String userId) {
        ProductDTO productDTO = productService.getProduct(cartRequestDTO.getProductId());
        if (productDTO == null) {
            return null;
        }
        Cart cart = CartMapper.getInstance().toEntity(productDTO, userId, cartRequestDTO.getQuantity());
        cartRepository.save(cart);
        return cart;
    }

    @Override
    public List<CartResponseDTO> getAll(String userId) {
        List<Cart> carts = cartRepository.findAll();

        List<CartResponseDTO> cartDTOs = carts.stream().map(cart -> {
            ProductDTO productDTO = productService.getProduct(cart.getProductId());
            return CartMapper.getInstance().toDTO(cart, productDTO);
        }).collect(Collectors.toList());

        return cartDTOs;
    }

    @Override
    public void deleteCart(String id) {
        cartRepository.deleteById(id);
    }

    @Override
    public Cart getCartById(String cartId) {
        Optional<Cart> cartOptional = cartRepository.findById(cartId);
        return cartOptional.isPresent() ? cartOptional.get() : new Cart();
    }

    @Override
    public Cart updateCart(String id, CartRequestDTO cartRequestDTO) {
        Optional<Cart> cartOptional = cartRepository.findById(id);
        if (!cartOptional.isPresent()) {
            return null;
        }
        Cart cart = cartOptional.get();
        cart.setQuantity(cartRequestDTO.getQuantity());
        cartRepository.save(cart);
        return cart;
    }
}
