package com.internproject.orderservice.repository;

import com.internproject.orderservice.entity.Cart;
import com.internproject.orderservice.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ICartRepository extends JpaRepository<Cart, String> {

}
