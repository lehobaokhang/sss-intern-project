package com.internproject.orderservice.service.impl;

import com.internproject.orderservice.dto.cart.CartRequestDTO;
import com.internproject.orderservice.dto.cart.CartResponseDTO;
import com.internproject.orderservice.dto.product.GetByIdsRequest;
import com.internproject.orderservice.dto.product.ProductDTO;
import com.internproject.orderservice.entity.Cart;
import com.internproject.orderservice.mapper.CartMapper;
import com.internproject.orderservice.repository.ICartRepository;
import com.internproject.orderservice.service.ICartService;
import com.internproject.orderservice.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
        if (productDTO.getSellerId().equals(userId)) {
            return null;
        }
        Cart cart = CartMapper.getInstance().toEntity(cartRequestDTO);
        cart.setUserId(userId);
        try {
            cartRepository.save(cart);
        } catch (Exception e) {
            return null;
        }
        return cart;
    }

    @Override
    public List<CartResponseDTO> getAll(String userId) {
        List<Cart> carts = cartRepository.findByUserId(userId);

        List<String> productIds = carts.stream().map(cart -> cart.getProductId()).collect(Collectors.toList());
        List<ProductDTO> productDTOList = productService.getProductByIds(new GetByIdsRequest(productIds));

        List<CartResponseDTO> response = new ArrayList<>();

        for (int i = 0; i < carts.size(); i++) {
            CartResponseDTO cart = CartMapper.getInstance().toDTO(carts.get(i));
            cart.setProductName(productDTOList.get(i).getProductName());
            cart.setPrice(productDTOList.get(i).getPrice());
            cart.setProductImage(productDTOList.get(i).getProductImage());
            cart.setCategory(productDTOList.get(i).getCategory());
            cart.setSellerId(productDTOList.get(i).getSellerId());
            response.add(cart);
        }

        return response;
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
