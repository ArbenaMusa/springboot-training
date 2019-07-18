package com.ucx.training.shop.controller;

import com.ucx.training.shop.dto.InvoiceDTO;
import com.ucx.training.shop.dto.LineItemDTO;
import com.ucx.training.shop.entity.Invoice;
import com.ucx.training.shop.exception.ResponseException;
import com.ucx.training.shop.service.InvoiceService;
import com.ucx.training.shop.service.LineItemService;
import com.ucx.training.shop.util.InvoiceUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log4j2
@RestController
@RequestMapping("invoices")
public class InvoiceController {

    private LineItemService lineItemService;
    private InvoiceService invoiceService;

    public InvoiceController(LineItemService lineItemService, InvoiceService invoiceService) {
        this.lineItemService = lineItemService;
        this.invoiceService = invoiceService;
    }

    @PostMapping
    public InvoiceDTO create(@RequestBody Invoice invoice) throws ResponseException {
        try {
            return InvoiceUtil.getInvoice(invoiceService.save(invoice));
        } catch (Exception e) {
            throw new ResponseException(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("{id}")
    public InvoiceDTO update(@RequestBody Invoice invoice, @PathVariable Integer id) throws ResponseException {
        try {
            Invoice updatedInvoice = invoiceService.update(invoice.getLineItemList(), invoice.getCostumer(), invoice);
            return InvoiceUtil.getInvoice(updatedInvoice);
        } catch (Exception e) {
            throw new ResponseException(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("{id}")
    public InvoiceDTO findInvoiceById(@PathVariable Integer id) throws ResponseException {
        try {
            return InvoiceUtil.getInvoice(invoiceService.findById(id));
        } catch (Exception e) {
            throw new ResponseException(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/lineitems/{invoiceId}")
    public List<LineItemDTO> getLineItemsByInvoiceId(@PathVariable Integer invoiceId) throws ResponseException {
        try {
            return lineItemService.findAllByInvoiceId(invoiceId);
        } catch (Exception e) {
            throw new ResponseException(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("{id}")
    public void remove(@PathVariable Integer id) throws ResponseException {
        try {
            invoiceService.remove(id);
        } catch (Exception e) {
            throw new ResponseException(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
