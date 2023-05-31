package com.internproject.productservice.repository;

import com.internproject.productservice.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface IProductRepository extends JpaRepository<Product, String> {

    @Modifying
    void deleteProduct(@Param("productId") String productId,
                       @Param("sellerId") String userId);

    List<Product> getAllProduct();

    Optional<Product> getProduct(@Param("id") String id);

    @Modifying
    @Query("UPDATE Product p SET p.quantity = p.quantity - :quantity WHERE p.id = :productId")
    void decreaseQuantity(@Param("productId") String productId, @Param("quantity") int quantity);

    List<Product> findByProductNameContainingIgnoreCase(String productName);

    List<Product> filterByCategoryAndPrice(@Param("categoryId") String categoryId,
                                           @Param("minPrice") Integer minPrice,
                                           @Param("maxPrice") Integer maxPrice);
}
