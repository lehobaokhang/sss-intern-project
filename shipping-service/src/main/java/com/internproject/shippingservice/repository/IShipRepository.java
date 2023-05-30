package com.internproject.shippingservice.repository;

import com.internproject.shippingservice.entity.Ship;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IShipRepository extends JpaRepository<Ship, String> {
    Optional<Ship> findByOrderId(String orderId);
}
