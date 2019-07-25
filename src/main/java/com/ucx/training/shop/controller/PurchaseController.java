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
import com.ucx.training.shop.util.uimapper.InvoiceMapper;
import com.ucx.training.shop.util.uimapper.LineItemMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Log4j2
@RestController
@RequestMapping("v1/purchases")
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
        } catch (IllegalArgumentException | NotFoundException e) {
            throw new ResponseException(e.getMessage(), HttpStatus.BAD_REQUEST);
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
            invoiceDTO.setLineItemList(converToDTOList(invoice.getLineItemList()));
        } catch (NotFoundException | IllegalArgumentException e) {
            throw new ResponseException(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            throw new ResponseException(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return invoiceDTO;
    }

    private LineItemDTO convertToDTO(LineItem lineItemList) {
        LineItemDTO lineItemDTO = LineItemDTO.builder()
                .product(lineItemList.getProduct().getName())
                .invoiceId(lineItemList.getInvoice().getId())
                .quantity(lineItemList.getQuantity())
                .build();
        return lineItemDTO;
    }

    private List<LineItemDTO> converToDTOList(List<LineItem> lineItemList) {
        List<LineItemDTO> lineItemDTOS = lineItemList.stream()
                .map(lineItem -> convertToDTO(lineItem))
                .collect(Collectors.toList());
        return lineItemDTOS;
    }

    @DeleteMapping("lineitems/{id}")
    public LineItemDTO cancelLineItem(@PathVariable Integer id) throws ResponseException {
        try {
            LineItem lineItem = purchaseService.cancelLineItem(id);
            LineItemDTO lineItemDTO = LineItemMapper.getLineItem(lineItem, lineItem.getProduct());
            return lineItemDTO;
        } catch (NotFoundException | IllegalArgumentException e) {
            throw new ResponseException(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            throw new ResponseException(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("{id}")
    public InvoiceDTO cancelPurchase(@PathVariable Integer id) throws ResponseException {
        try {
            Invoice invoice = purchaseService.cancelPurchase(id);
            InvoiceDTO invoiceDTO = InvoiceMapper.getInvoice(invoice);
            return invoiceDTO;
        } catch (NotFoundException | IllegalArgumentException e) {
            throw new ResponseException(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            throw new ResponseException(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping("lineitems/{lineItemId}")
    public LineItemDTO changeQuantity(@RequestBody LineItem lineItem, @PathVariable Integer lineItemId) throws NotFoundException, ResponseException {
        try {
            LineItem updatedLineItem = purchaseService.changeQuantity(lineItem, lineItemId);
            return new LineItemDTO(updatedLineItem.getProduct().getName(), updatedLineItem.getQuantity(), updatedLineItem.getInvoice().getId());
        } catch (NotFoundException | IllegalArgumentException e) {
            throw new ResponseException(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            throw new ResponseException(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
