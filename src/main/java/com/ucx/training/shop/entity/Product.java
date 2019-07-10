package com.ucx.training.shop.entity;

import com.ucx.training.shop.type.RecordStatus;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product extends BaseModel<Integer> {
    private String name;
    private BigDecimal unitPrice;
    private Boolean inStock;
    @CreationTimestamp
    private LocalDateTime createDateTime;
    @UpdateTimestamp
    private LocalDateTime updateDateTime;

    public Product(Integer id, RecordStatus recordStatus, String name, BigDecimal unitPrice, Boolean inStock) {
        super(id, recordStatus);
        this.name = name;
        this.unitPrice = unitPrice;
        this.inStock = inStock;
    }
}