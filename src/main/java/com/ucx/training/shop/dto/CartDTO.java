package com.ucx.training.shop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartDTO implements DTOEntity{
    private Integer productId;
    private Integer quantity;
    private Integer invoiceId;
}
