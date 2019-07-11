package com.ucx.training.shop.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Invoice extends BaseModel<Integer> {

    private Integer invoiceNumber;
    @OneToOne(optional = false)
    @JoinColumn
    private Costumer costumer;

    @OneToMany
    @JoinTable(name = "invoice_lineitem",
            joinColumns = @JoinColumn(name = "lineitem_id"),
            inverseJoinColumns = @JoinColumn(name = "invoice_id"))
    private List<LineItem> lineItemList;
    private BigDecimal total;

}
