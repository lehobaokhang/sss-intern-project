package com.internproject.shippingservice.entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "tracking")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class Tracking {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id", nullable = false)
    private String id;

    @ManyToOne()
    @JoinColumn(name = "ship_id", nullable = false)
    private Ship ship;

    @Column(name = "locate", nullable = false)
    private String locate;
}