package com.internproject.productservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "product")
@Data
@AllArgsConstructor
@NoArgsConstructor
@NamedQuery(name = "Product.deleteProduct", query = "UPDATE Product p SET p.deleted = True WHERE p.id = :productId AND p.sellerId = :sellerId")
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

//    @OneToMany(cascade = CascadeType.ALL)
//    @JoinColumn(name = "product_id", nullable = false)
//    private List<ProductOption> options;
}
