package com.ucx.training.shop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LineItemDTO {
    private String product;
    private Integer quantity;
    private Integer invoiceId;
}
