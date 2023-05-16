package com.internproject.orderservice.repository;

import com.internproject.orderservice.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ICartRepository extends JpaRepository<Cart, String> {
    Optional<Cart> findByUserIdAndProductId(String userId, String productId);
}
