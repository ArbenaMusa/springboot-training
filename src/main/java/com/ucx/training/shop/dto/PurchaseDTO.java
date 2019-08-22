package com.ucx.training.shop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseDTO extends DTOEntity{
    private Integer costumerId;
    private Integer orderId;
    private List<CartDTO> cart;
}
