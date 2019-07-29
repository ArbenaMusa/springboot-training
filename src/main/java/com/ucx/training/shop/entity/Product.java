package com.ucx.training.shop.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product extends BaseEntity<Integer> {
    @Column(unique = true)
    private String name;
    private BigDecimal unitPrice;
    private Integer inStock;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @OneToOne(mappedBy = "product")
    private FileUpload fileUpload;

    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private Category category;

    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private Brand brand;
}
