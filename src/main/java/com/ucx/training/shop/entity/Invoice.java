package com.ucx.training.shop.entity;

import com.ucx.training.shop.type.RecordStatus;
import lombok.*;

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

    @OneToOne
    @JoinColumn

    private Costumer costumer;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "invoice_lineitems",
            joinColumns = @JoinColumn(name = "invoice_id"),
            inverseJoinColumns = @JoinColumn(name = "lineitem_id"))
    private List<LineItem> lineItemList;

    private BigDecimal total;

    public Invoice(Integer id, RecordStatus recordStatus, Integer invoiceNumber, Costumer costumer, List<LineItem> list) {
        super(id, recordStatus);
        this.invoiceNumber = invoiceNumber;
        this.costumer = costumer;
        this.lineItemList = list;
    }

    @Override
    public String toString() {
        return String.format("Invoice number: %d, Costumer: %s, LineItem lineItemList: %s, Total: %f", getInvoiceNumber(), getCostumer(), getLineItemList(), getTotal());
    }
}
