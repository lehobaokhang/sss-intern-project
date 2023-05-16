package com.internproject.orderservice.service.impl;

import com.internproject.orderservice.dto.AddToCartDTO;
import com.internproject.orderservice.dto.ProductDTO;
import com.internproject.orderservice.entity.Cart;
import com.internproject.orderservice.mapper.CartMapper;
import com.internproject.orderservice.repository.ICartRepository;
import com.internproject.orderservice.service.ICartService;
import com.internproject.orderservice.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartServiceImpl implements ICartService {
    @Autowired
    private IProductService productService;

    @Autowired
    private ICartRepository cartRepository;

    @Override
    public Cart addCart(AddToCartDTO addToCartDTO, String userId) {
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
}
