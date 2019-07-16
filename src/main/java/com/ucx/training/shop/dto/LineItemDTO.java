package com.ucx.training.shop.dto;

import com.ucx.training.shop.entity.Invoice;
import com.ucx.training.shop.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;
@Data
public class LineItemDTO {
    private ProductDTO product;
    private Integer quantity;
    private BigDecimal lineItemTotal; //Product quantity * Product unitPrice

}
