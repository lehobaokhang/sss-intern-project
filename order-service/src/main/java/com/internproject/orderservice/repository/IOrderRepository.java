package com.internproject.orderservice.repository;

import com.internproject.orderservice.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IOrderRepository extends JpaRepository<Order, String> {
    List<Order> findByUserId(String userId);
}
