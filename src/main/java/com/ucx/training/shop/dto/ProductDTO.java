package com.ucx.training.shop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO extends DTOEntity{
    private String name;
    private BigDecimal unitPrice;
    private String fileName;
    private PlatformDTO platform;
    private BrandDTO brand;
}
