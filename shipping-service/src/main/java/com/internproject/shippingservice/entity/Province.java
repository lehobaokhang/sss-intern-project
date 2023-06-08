package com.internproject.shippingservice.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "provinces")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class Province {
    @Id
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "province_name", nullable = false)
    private String provinceName;

    @Column(name = "province_full_name", nullable = false)
    private String provinceFullName;
    
    @OneToMany(mappedBy = "province")
    private List<District> districts;
}
