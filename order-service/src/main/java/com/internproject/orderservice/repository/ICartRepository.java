package com.internproject.orderservice.repository;

import com.internproject.orderservice.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICartRepository extends JpaRepository<Cart, String> {
}
