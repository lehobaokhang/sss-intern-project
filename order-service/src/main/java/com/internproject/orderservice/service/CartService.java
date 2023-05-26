package com.internproject.orderservice.service;

import com.internproject.orderservice.dto.CartDTO;
import com.internproject.orderservice.dto.cart.CartResponse;
import com.internproject.orderservice.dto.product.GetByIds;
import com.internproject.orderservice.dto.product.ProductDTO;
import com.internproject.orderservice.entity.Cart;
import com.internproject.orderservice.exception.CartException;
import com.internproject.orderservice.exception.CartNotFoundException;
import com.internproject.orderservice.exception.ProductNotFoundException;
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
    private IProductService productService;
    private ICartRepository cartRepository;
    private CartMapstruct cartMapstruct;

    @Autowired
    public CartService(IProductService productService, ICartRepository cartRepository, CartMapstruct cartMapstruct) {
        this.productService = productService;
        this.cartRepository = cartRepository;
        this.cartMapstruct = cartMapstruct;
    }

    public Cart addCart(CartDTO cartDTO, String userId) {
        ProductDTO productDTO = productService.getProduct(cartDTO.getProductId());
        if (productDTO == null) {
            throw new ProductNotFoundException(String.format("Can not find any product with id: %s", cartDTO.getProductId()));
        }
        if (productDTO.getSellerId().equals(userId)) {
            throw new CartException("Can not add product of yourself to your cart");
        }
        Cart cart = cartMapstruct.toCart(cartDTO);
        cart.setUserId(userId);
        try {
            cartRepository.save(cart);
        } catch (Exception e) {
            throw new CartException("This product in your cart yet");
        }
        return cart;
    }

    public List<CartResponse> getAll(String userId) {
        List<Cart> carts = cartRepository.findByUserId(userId);
        List<String> productIds = carts.stream().map(cart -> cart.getProductId()).collect(Collectors.toList());
        List<ProductDTO> productDTOList = productService.getProductByIds(new GetByIds(productIds));
        List<CartResponse> response = new ArrayList<>();
        for (int i = 0; i < carts.size(); i++) {
            CartResponse cartResponse = new CartResponse();
            cartResponse.setCartId(carts.get(i).getId());
            cartResponse.setQuantity(carts.get(i).getQuantity());
            cartResponse.setProductDTO(productDTOList.get(i));
            response.add(cartResponse);
        }
        return response;
    }

    @Transactional
    public void deleteCart(String id, String userId) {
        cartRepository.deleteByIdAndUserId(id, userId);
    }

//    public Cart getCartById(String cartId) {
//        Optional<Cart> cartOptional = cartRepository.findById(cartId);
//        return cartOptional.isPresent() ? cartOptional.get() : new Cart();
//    }

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
}
