package com.ucx.training.shop.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product extends BaseEntity<Integer> {
    private String name;
    private BigDecimal unitPrice;
    private Integer inStock;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @OneToOne
    private FileUpload fileUpload;
}
