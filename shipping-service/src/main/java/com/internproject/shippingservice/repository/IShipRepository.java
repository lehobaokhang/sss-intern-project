package com.internproject.shippingservice.repository;

import com.internproject.shippingservice.entity.Ship;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IShipRepository extends JpaRepository<Ship, String> {
}
