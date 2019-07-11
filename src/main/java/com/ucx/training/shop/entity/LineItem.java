package com.ucx.training.shop.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LineItem extends BaseModel<Integer> {

    @ManyToMany(mappedBy = "lineItemList")
    private List<Invoice> invoiceList;
    @OneToOne
    @JoinColumn
    private Product product;
    private Integer quantity;
    private BigDecimal lineItemTotal; //Product quantity * Product unitPrice


}