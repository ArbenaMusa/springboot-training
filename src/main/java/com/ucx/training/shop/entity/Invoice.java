package com.ucx.training.shop.entity;

import com.ucx.training.shop.type.RecordStatus;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import java.math.BigDecimal;
import java.time.LocalDateTime;
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

    @ManyToMany(mappedBy = "invoiceList")
    private List<LineItem> list;
    private BigDecimal total;
    @CreationTimestamp
    private LocalDateTime createDateTime;
    @UpdateTimestamp
    private LocalDateTime updateDateTime;

    public Invoice(Integer id, RecordStatus recordStatus, Integer invoiceNumber, Costumer costumer, List<LineItem> list) {
        super(id, recordStatus);
        this.invoiceNumber = invoiceNumber;
        this.costumer = costumer;
        this.list = list;
    }
}
