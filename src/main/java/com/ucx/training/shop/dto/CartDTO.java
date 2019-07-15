package com.ucx.training.shop.dto;

import lombok.Data;

@Data
public class CartDTO {
    private Integer productId;
    private Integer quantity;
    private Integer invoiceId;
}
