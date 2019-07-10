package com.ucx.training.shop.entity;

import com.ucx.training.shop.type.RecordStatus;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LineItem extends BaseModel<Integer> {

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "invoice_lineitem",
            joinColumns = @JoinColumn(name = "lineitem_id"),
            inverseJoinColumns = @JoinColumn(name = "invoice_id"))
    private List<Invoice> invoiceList;
    @OneToOne
    @JoinColumn
    private Product product;
    private Integer quantity;
    @CreationTimestamp
    private LocalDateTime createDateTime;
    @UpdateTimestamp
    private LocalDateTime updateDateTime;

    public LineItem(Integer id, RecordStatus recordStatus, List<Invoice> invoiceList, Product product, Integer quantity) {
        super(id, recordStatus);
        this.invoiceList = invoiceList;
        this.product = product;
        this.quantity = quantity;
    }
}