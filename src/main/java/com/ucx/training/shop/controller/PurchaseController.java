package com.ucx.training.shop.controller;

import com.ucx.training.shop.dto.CartDTO;
import com.ucx.training.shop.dto.InvoiceDTO;
import com.ucx.training.shop.dto.PurchaseDTO;
import com.ucx.training.shop.entity.Invoice;
import com.ucx.training.shop.service.PurchaseService;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Log4j2
@RestController
@RequestMapping("purchase")
public class PurchaseController {

    private PurchaseService purchaseService;

    public PurchaseController(PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }

    @PostMapping("addtocart")
    public Map<String, Integer> addToCart(@RequestBody CartDTO cartDTO) {
        Map<String, Integer> resultMap = new HashMap<>();
        try {
            Integer invoiceId = purchaseService.addToCart(cartDTO.getProductId(), cartDTO.getQuantity(), cartDTO.getInvoiceId());
            resultMap.put("invoiceId", invoiceId);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return resultMap;
    }

    @PostMapping
    public InvoiceDTO buy(@RequestBody PurchaseDTO purchaseDTO) {
        InvoiceDTO invoiceDTO = new InvoiceDTO();
        try{
            Invoice invoice = purchaseService.buy(purchaseDTO.getCostumerId(), purchaseDTO.getInvoiceId());
            invoiceDTO.setCreatedDateTime(invoice.getCreateDateTime());
            invoiceDTO.setCostumerName(invoice.getCostumer().getName());
            invoiceDTO.setInvoiceNumber(invoice.getInvoiceNumber());
            invoiceDTO.setTotal(invoice.getTotal());
        } catch(Exception e){
            log.error(String.format("An error occured while purchasing:%n Costumer ID: %d, Invoice ID: %d", purchaseDTO.getCostumerId(), purchaseDTO.getInvoiceId()));
        }
        return invoiceDTO;
    }
}
