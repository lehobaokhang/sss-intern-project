package com.internproject.shippingservice.repository;

import com.internproject.shippingservice.entity.Rating;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IRatingRepository extends JpaRepository<Rating, String> {
    List<Rating> findByProductId(String id);
}
