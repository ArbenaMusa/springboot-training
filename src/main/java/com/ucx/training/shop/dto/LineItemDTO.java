package com.ucx.training.shop.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class LineItemDTO {
    private ProductDTO productDTO;
    private Integer quantity;
    private BigDecimal total;
}
