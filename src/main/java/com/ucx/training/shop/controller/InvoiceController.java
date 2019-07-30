package com.ucx.training.shop.controller;

import com.ucx.training.shop.dto.DTOEntity;
import com.ucx.training.shop.dto.InvoiceDTO;
import com.ucx.training.shop.dto.LineItemDTO;
import com.ucx.training.shop.entity.Invoice;
import com.ucx.training.shop.entity.LineItem;
import com.ucx.training.shop.exception.NotFoundException;
import com.ucx.training.shop.exception.ResponseException;
import com.ucx.training.shop.service.InvoiceService;
import com.ucx.training.shop.service.LineItemService;
import com.ucx.training.shop.util.uimapper.DTOMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log4j2
@RestController
@RequestMapping("v1/invoices")
public class InvoiceController {

    private LineItemService lineItemService;
    private InvoiceService invoiceService;

    public InvoiceController(LineItemService lineItemService, InvoiceService invoiceService) {
        this.lineItemService = lineItemService;
        this.invoiceService = invoiceService;
    }

    @PutMapping("{id}")
    public DTOEntity update(@RequestBody Invoice invoice, @PathVariable Integer id) throws ResponseException {
        try {
            Invoice updatedInvoice = invoiceService.update(invoice.getLineItemList(), invoice.getCustomer(), invoice);
            return new DTOMapper().convertToDto(updatedInvoice, new InvoiceDTO());
        } catch (IllegalArgumentException e) {
            throw new ResponseException(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            throw new ResponseException(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/lineitems/{invoiceId}")
    public List<DTOEntity> getLineItemsByInvoiceId(@PathVariable Integer invoiceId) throws ResponseException {
        try {
            List<LineItem> lineItems = lineItemService.findAllByInvoiceId(invoiceId);
            return DTOMapper.converToDTOList(lineItems,new LineItemDTO());
        } catch (IllegalArgumentException | NotFoundException e) {
            throw new ResponseException(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            throw new ResponseException(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("{id}")
    public DTOEntity findById(@PathVariable Integer id) throws ResponseException {
        try {
            Invoice invoice = invoiceService.findById(id);
            return new DTOMapper().convertToDto(invoice, new InvoiceDTO());
        } catch (Exception e) {
            throw new ResponseException(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public List<DTOEntity> findAllSorted(@RequestParam(required = false, defaultValue = "ASC") String direction, @PathVariable Integer id, @RequestParam(defaultValue = "id") String... properties) throws ResponseException {
        try {
            List<Invoice> invoiceList = invoiceService.findAllSorted(direction, properties);
            return DTOMapper.converToDTOList(invoiceList,new InvoiceDTO());
        } catch (Exception e) {
            throw new ResponseException(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
