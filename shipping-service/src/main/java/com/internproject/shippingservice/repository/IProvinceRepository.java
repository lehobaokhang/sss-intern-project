package com.internproject.shippingservice.repository;

import com.internproject.shippingservice.entity.Province;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IProvinceRepository extends JpaRepository<Province, Integer> {
}
