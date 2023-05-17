package com.internproject.orderservice.service.impl;

import com.internproject.orderservice.dto.AddAndUpdateCartDTO;
import com.internproject.orderservice.dto.CartDTO;
import com.internproject.orderservice.dto.ProductDTO;
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
    public Cart addCart(AddAndUpdateCartDTO addToCartDTO, String userId) {
        ProductDTO productDTO = productService.getProduct(addToCartDTO.getProductId());

        if (productDTO == null) {
            return null;
        }

        Cart cart;
        Optional<Cart> cartOptional = cartRepository.findByUserIdAndProductId(userId, productDTO.getId());
        if (cartOptional.isPresent()) {
            cart = cartOptional.get();
            cart.setQuantity(cart.getQuantity() + addToCartDTO.getQuantity());
            cartRepository.save(cart);
        } else {
            cart = CartMapper.getInstance().toEntity(addToCartDTO);
            cart.setUserId(userId);
            cartRepository.save(cart);
        }

        return cart;
    }

    @Override
    public Cart updateCart(AddAndUpdateCartDTO updateCartDTO, String userId) {
        Optional<Cart> cartOptional = cartRepository.findByUserIdAndProductId(userId, updateCartDTO.getProductId());

        if (!cartOptional.isPresent()) {
            return null;
        }

        Cart cart = cartOptional.get();
        cart.setQuantity(updateCartDTO.getQuantity());
        cartRepository.save(cart);

        return cart;
    }

    @Override
    public List<CartDTO> getAll(String userId) {
        List<Cart> carts = cartRepository.findAll();

        List<CartDTO> cartDTOs = carts.stream().
                map(cart -> {
                    ProductDTO productDTO = productService.getProduct(cart.getProductId());
                    return CartMapper.getInstance().toDTO(cart, productDTO);
                }).
                collect(Collectors.toList());

        return cartDTOs;
    }

    @Override
    public void deleteCart(String id) {
        cartRepository.deleteById(id);
    }
}
