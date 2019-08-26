package com.ucx.training.shop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseDTO extends DTOEntity{
    private Integer customerId;
    private List<CartDTO> cart;
    private BigDecimal total;
    private AddressDTO address;
}
