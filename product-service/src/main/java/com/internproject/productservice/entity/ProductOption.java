package com.internproject.productservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "option")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductOption {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "option_name", nullable = false)
    private String optionName;

    @OneToMany
    @JoinColumn(name = "option_id", nullable = false)
    private List<OptionDetail> optionDetails;
}
