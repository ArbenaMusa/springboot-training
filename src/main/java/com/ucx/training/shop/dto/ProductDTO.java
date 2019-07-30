package com.ucx.training.shop.dto;

import com.ucx.training.shop.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO implements DTOEntity{
    private String name;
    private BigDecimal unitPrice;
    private String fileName;
    private String category;
    private String brand;
}
