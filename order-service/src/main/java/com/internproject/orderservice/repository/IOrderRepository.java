package com.internproject.orderservice.repository;

import com.internproject.orderservice.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IOrderRepository extends JpaRepository<Order, String> {
}
