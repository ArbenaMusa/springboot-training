package com.ucx.training.shop.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductDTO {
private String name;
private BigDecimal unitPrice;
private String fileName;
}
