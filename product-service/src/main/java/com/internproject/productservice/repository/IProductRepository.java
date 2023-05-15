package com.internproject.productservice.repository;

import com.internproject.productservice.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IProductRepository extends JpaRepository<Product, String> {
    @Modifying
    @Query(name = "Product.deleteProduct")
    void deleteById(@Param("productId") String productId);
}
