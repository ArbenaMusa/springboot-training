package com.ucx.training.shop.entity;

import com.ucx.training.shop.type.RecordStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Product extends BaseModel<Integer> {
    private String name;
    private BigDecimal unitPrice;
    private Boolean inStock;

    @Builder
    public Product(Integer id, RecordStatus recordStatus, String name, BigDecimal unitPrice, Boolean inStock) {
        super(id, recordStatus);
        this.name = name;
        this.unitPrice = unitPrice;
        this.inStock = inStock;
    }

    @Override
    public String toString() {
        return String.format("%s, %f, in stock: %b", getName(), getUnitPrice(), getInStock());
    }
}
