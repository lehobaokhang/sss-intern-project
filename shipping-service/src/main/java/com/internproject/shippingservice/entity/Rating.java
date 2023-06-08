package com.internproject.shippingservice.entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "rating")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class Rating {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "user_id", nullable = false)
    private String userId;

    @Column(name = "product_id", nullable = false)
    private String productId;

    @Column(name = "rating", nullable = false)
    private int rate;

    @Column(name = "review", nullable = false)
    private String review;
}
