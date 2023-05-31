package com.internproject.productservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@Entity
@Table(name = "product")
@Data
@AllArgsConstructor
@NoArgsConstructor
@NamedQuery(name = "Product.deleteProduct", query = "UPDATE Product p SET p.deleted = True WHERE p.id = :productId AND p.sellerId = :sellerId")
@NamedQuery(name = "Product.getAllProduct", query = "SELECT p FROM Product p WHERE p.deleted = false")
@NamedQuery(name = "Product.getProduct", query = "SELECT p FROM Product p WHERE p.deleted = false AND p.id = :id")
@NamedQuery(name = "Product.getProductsByCategoryId", query = "SELECT p FROM Product p WHERE p.deleted = false AND p.category.id = :categoryId")
@NamedQuery(
        name = "Product.filterByCategoryAndPrice",
        query = "SELECT p FROM Product p WHERE p.deleted = false " +
                "AND (:categoryId IS NULL OR p.category.id = :categoryId) " +
                "AND (:minPrice IS NULL OR p.price >= :minPrice) " +
                "AND (:maxPrice IS NULL OR p.price <= :maxPrice)")
public class Product {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "product_name", nullable = false)
    private String productName;

    @Lob
    @Column(name = "product_image")
    @Type(type="org.hibernate.type.BinaryType")
    private byte[] productImage;

    @Column(name = "product_size", nullable = false)
    private String productSize;

    @Column(name = "product_weight", nullable = false)
    private int productWeight;

    @Column(name = "seller_id", nullable = false)
    private String sellerId;

    @ManyToOne()
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @Column(name = "is_deleted", nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    private boolean deleted;

    @Column(name = "price", nullable = false)
    private int price;

    @Column(name = "quantity", nullable = false)
    private int quantity;
}
