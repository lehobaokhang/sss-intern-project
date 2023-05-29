package com.internproject.shippingservice.repository;

import com.internproject.shippingservice.entity.District;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IDistrictRepository extends JpaRepository<District, Integer> {
}
