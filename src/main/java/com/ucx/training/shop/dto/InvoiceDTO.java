package com.ucx.training.shop.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class InvoiceDTO {
    private LocalDateTime createdDateTime;
    private Integer invoiceNumber;
    private BigDecimal total;
    private String costumerName;
}
