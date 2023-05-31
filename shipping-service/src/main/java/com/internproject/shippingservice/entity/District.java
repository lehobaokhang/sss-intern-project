package com.internproject.shippingservice.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "districts")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class District {
    @Id
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "district_name", nullable = false)
    private String districtName;

    @Column(name = "district_full_name", nullable = false)
    private String districtFullName;

    @ManyToOne()
    @JoinColumn(name = "province_id", nullable = false)
    private Province province;
}

