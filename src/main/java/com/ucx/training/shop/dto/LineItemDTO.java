package com.ucx.training.shop.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LineItemDTO implements DTOEntity{
    private String product;
    private Integer quantity;
    private Integer invoiceId;

    @Builder
    public LineItemDTO(String product, Integer quantity, Integer invoiceId) {
        this.product = product;
        this.quantity = quantity;
        this.invoiceId = invoiceId;
    }
}
