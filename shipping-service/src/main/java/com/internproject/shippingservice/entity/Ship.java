package com.internproject.shippingservice.entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "ship")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class Ship {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "order_id", nullable = false)
    private String orderId;

    @Column(name = "status", nullable = false)
    private String status;

    @OneToMany(mappedBy = "ship")
    private List<Tracking> tracking;
}
