package com.ucx.training.shop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LineItemDTO {
    private String product;
    private Integer quantity;
    private Integer invoiceId;
}
