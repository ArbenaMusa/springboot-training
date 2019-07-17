package com.ucx.training.shop.controller;

import com.ucx.training.shop.dto.CartDTO;
import com.ucx.training.shop.dto.InvoiceDTO;
import com.ucx.training.shop.dto.LineItemDTO;
import com.ucx.training.shop.dto.PurchaseDTO;
import com.ucx.training.shop.entity.Invoice;
import com.ucx.training.shop.entity.LineItem;
import com.ucx.training.shop.exception.NotFoundException;
import com.ucx.training.shop.exception.ResponseException;
import com.ucx.training.shop.service.LineItemService;
import com.ucx.training.shop.service.PurchaseService;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Log4j2
@RestController
@RequestMapping("purchases")
public class PurchaseController {

    private PurchaseService purchaseService;
    private LineItemService lineItemService;

    public PurchaseController(PurchaseService purchaseService, LineItemService lineItemService) {
        this.purchaseService = purchaseService;
        this.lineItemService = lineItemService;
    }

    @PostMapping("lineitems")
    public Map<String, Integer> addToCart(@RequestBody CartDTO cartDTO) throws ResponseException {
        Map<String, Integer> resultMap = new HashMap<>();
        try {
            Integer invoiceId = purchaseService.addToCart(cartDTO.getProductId(), cartDTO.getQuantity(), cartDTO.getInvoiceId());
            resultMap.put("invoiceId", invoiceId);
        } catch (Exception e) {
            throw new ResponseException(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return resultMap;
    }

    @PostMapping
    public InvoiceDTO buy(@RequestBody PurchaseDTO purchaseDTO) throws ResponseException {
        InvoiceDTO invoiceDTO = new InvoiceDTO();
        try {
            Invoice invoice = purchaseService.buy(purchaseDTO.getCostumerId(), purchaseDTO.getInvoiceId());
            invoiceDTO.setCreatedDateTime(invoice.getCreateDateTime());
            invoiceDTO.setCostumerName(invoice.getCostumer().getName());
            invoiceDTO.setInvoiceNumber(invoice.getInvoiceNumber());
            invoiceDTO.setTotal(invoice.getTotal());
        } catch (Exception e) {
            throw new ResponseException(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return invoiceDTO;
    }

    @DeleteMapping("lineitems/{id}")
    public LineItem cancelLineItem(@PathVariable Integer id) throws ResponseException {
        LineItem lineItem = null;
        try {
            lineItem = purchaseService.cancelLineItem(id);
        } catch (Exception e) {
            throw new ResponseException(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return lineItem;
    }

    @DeleteMapping("{id}")
    public Invoice cancelPurchase(@PathVariable Integer id) throws ResponseException {
        Invoice invoice = null;
        try {
            invoice = purchaseService.cancelPurchase(id);
        } catch (Exception e) {
            throw new ResponseException(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return invoice;
    }

    @PatchMapping("lineitems/{lineItemId}")
    public LineItemDTO changeQuantity(@RequestBody LineItem lineItem, @PathVariable Integer lineItemId) throws NotFoundException {
        LineItem updatedLineItem = purchaseService.changeQuantity(lineItem, lineItemId);
        return new LineItemDTO(updatedLineItem.getProduct().getName(), updatedLineItem.getQuantity(), updatedLineItem.getInvoice().getId());
    }
}
