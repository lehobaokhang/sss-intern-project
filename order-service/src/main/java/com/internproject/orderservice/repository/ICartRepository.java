package com.internproject.orderservice.repository;

import com.internproject.orderservice.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ICartRepository extends JpaRepository<Cart, String> {
    List<Cart> findByUserId(String userId);

    void deleteByIdAndUserId(String id, String userId);
}
