package com.internproject.orderservice.service.impl;

import com.internproject.orderservice.dto.cart.AddCartDTO;
import com.internproject.orderservice.dto.cart.CartDTO;
import com.internproject.orderservice.dto.product.OptionDetailDTO;
import com.internproject.orderservice.dto.product.ProductDTO;
import com.internproject.orderservice.entity.Cart;
import com.internproject.orderservice.mapper.CartMapper;
import com.internproject.orderservice.repository.ICartRepository;
import com.internproject.orderservice.service.ICartService;
import com.internproject.orderservice.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements ICartService {
    @Autowired
    private IProductService productService;

    @Autowired
    private ICartRepository cartRepository;

    @Override
    public Cart addCart(AddCartDTO addToCartDTO, String userId) {
        ProductDTO productDTO = productService.getProduct(addToCartDTO.getProductId());

        if (productDTO == null) {
            return null;
        }

        boolean checkDetailId = Arrays.stream(addToCartDTO.getDetails())
                .allMatch(id -> productDTO.getOptions().values().stream()
                        .flatMap(Set::stream)
                        .anyMatch(optionDetail -> optionDetail.getId().equals(id))
                );

        long isSameOption = productDTO.getOptions().values().stream()
                .filter(optionDetailDTOS -> Arrays.stream(addToCartDTO.getDetails())
                        .filter(id -> optionDetailDTOS.stream()
                                .anyMatch(optionDetail -> optionDetail.getId().equals(id)))
                        .count() >= 2).count();

        String[] details = productDTO.getOptions().values().stream()
                .flatMap(Set::stream)
                .filter(optionDetail -> Arrays.stream(addToCartDTO.getDetails()).anyMatch(id -> id.equals(optionDetail.getId())))
                .map(OptionDetailDTO::getOption_detail_name)
                .toArray(String[]::new);

        int price = productDTO.getOptions().values().stream()
                .flatMap(Set::stream)
                .filter(optionDetail -> Arrays.stream(addToCartDTO.getDetails()).anyMatch(id -> id.equals(optionDetail.getId())))
                .mapToInt(OptionDetailDTO::getOption_detail_price)
                .sum();

        if (isSameOption > 0) {
            return null;
        }

        if (!checkDetailId) {
            return null;
        }

        if (productDTO.getOptions().size() != addToCartDTO.getDetails().length) {
            return null;
        }

        Cart cart = CartMapper.getInstance().toEntity(addToCartDTO);
        cart.setUserId(userId);
        cart.setDetails(details);
        cart.setPrice(price);

        cartRepository.save(cart);
        return cart;
    }

    @Override
    public List<CartDTO> getAll(String userId) {
        List<Cart> carts = cartRepository.findAll();

        List<CartDTO> cartDTOs = carts.stream().map(cart -> {
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
}
