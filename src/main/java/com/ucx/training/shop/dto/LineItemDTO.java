package com.ucx.training.shop.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LineItemDTO {
    private Integer id;
    private String product;
    private Integer quantity;
    private Integer invoiceId;

    @Builder
    public LineItemDTO(Integer id, String product, Integer quantity, Integer invoiceId) {
        this.id=id;
        this.product = product;
        this.quantity = quantity;
        this.invoiceId = invoiceId;
    }
}
