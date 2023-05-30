package com.internproject.orderservice.repository;

import com.internproject.orderservice.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface IOrderRepository extends JpaRepository<Order, String> {
    List<Order> findByUserId(String userId);
    @Query("SELECT o FROM Order o JOIN o.orderProducts op WHERE o.userId = :userId AND op.productId = :productId")
    Optional<Order> findByUserIdAndProductId(@Param("userId") String userId, @Param("productId") String productId);
}
