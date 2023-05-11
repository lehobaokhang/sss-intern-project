package com.internproject.productservice.repository;

import com.internproject.productservice.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IProductRepository extends JpaRepository<Product, String> {
}
