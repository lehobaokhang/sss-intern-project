package com.internproject.shippingservice.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Time;
import java.sql.Timestamp;

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

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "locate")
    private District district;

    @Column(name = "tracking_at", nullable = false)
    @CreationTimestamp
    private Timestamp trackingAt;
}
