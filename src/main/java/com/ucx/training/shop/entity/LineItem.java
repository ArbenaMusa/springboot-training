package com.ucx.training.shop.entity;

import com.ucx.training.shop.type.RecordStatus;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LineItem extends BaseModel<Integer> {

    @ManyToMany(mappedBy = "lineItemList")
    //@JoinColumn(name = "Invoice", referencedColumnName = "id")
    private List<Invoice> invoice;

    @OneToOne
    @JoinColumn
    private Product product;

    private Integer quantity;

    public LineItem(Integer id, RecordStatus recordStatus, List<Invoice> invoiceList, Product product, Integer quantity) {
        super(id, recordStatus);
        this.invoice = invoiceList;
        this.product = product;
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return String.format("Product: %s, Quantity: %d", getProduct(), getQuantity());
    }
}
