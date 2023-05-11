package com.internproject.productservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "product")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "product_name", nullable = false)
    private String productName;

    @Column(name = "product_image", columnDefinition = "text", nullable = false)
    private String productImage;

    @Column(name = "product_size", nullable = false)
    private String size;

    @Column(name = "product_weight", nullable = false)
    private String productWeight;

    @Column(name = "seller_id", nullable = false)
    private String sellerId;

    @ManyToOne()
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(name = "is_deleted", nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    private boolean deleted;

    @OneToMany
    @JoinColumn(name = "product_id")
    private List<ProductOption> options;
}
