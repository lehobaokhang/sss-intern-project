package com.internproject.productservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "option_detail")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OptionDetail {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "option_detail_name", nullable = false)
    private String optionDetailName;

    @Column(name = "option_detail_price", nullable = false)
    private int optionDetailPrice;

    @Column(name = "option_detail_quantity", nullable = false)
    private int optionDetailQuantity;

    public OptionDetail(String optionDetailName, int optionDetailPrice, int optionDetailQuantity) {
        this.optionDetailName = optionDetailName;
        this.optionDetailPrice = optionDetailPrice;
        this.optionDetailQuantity = optionDetailQuantity;
    }
}
