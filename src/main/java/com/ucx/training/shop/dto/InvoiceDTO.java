package com.ucx.training.shop.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class InvoiceDTO extends DTOEntity {
    private LocalDateTime createdDateTime;
    private Integer invoiceNumber;
    private BigDecimal total;
    private String costumerName;
    private List<LineItemDTO> lineItemList;
}
