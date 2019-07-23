package com.ucx.training.shop.controller;

import com.ucx.training.shop.dto.InvoiceDTO;
import com.ucx.training.shop.dto.LineItemDTO;
import com.ucx.training.shop.entity.Invoice;
import com.ucx.training.shop.exception.NotFoundException;
import com.ucx.training.shop.exception.ResponseException;
import com.ucx.training.shop.service.InvoiceService;
import com.ucx.training.shop.service.LineItemService;
import com.ucx.training.shop.util.uimapper.InvoiceMapper;
import com.ucx.training.shop.util.uimapper.LineItemMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
            Invoice createdInvoice = invoiceService.save(invoice);
            return InvoiceMapper.getInvoice(createdInvoice);
        } catch (IllegalArgumentException e) {
            throw new ResponseException(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            throw new ResponseException(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("{id}")
    public InvoiceDTO update(@RequestBody Invoice invoice, @PathVariable Integer id) throws ResponseException {
        try {
            Invoice updatedInvoice = invoiceService.update(invoice.getLineItemList(), invoice.getCostumer(), invoice);
            return InvoiceMapper.getInvoice(updatedInvoice);
        } catch (IllegalArgumentException e) {
            throw new ResponseException(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            throw new ResponseException(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/lineitems/{invoiceId}")
    public List<LineItemDTO> getLineItemsByInvoiceId(@PathVariable Integer invoiceId) throws ResponseException {
        try {
            List <LineItemDTO> lineItemDTOs=new ArrayList<>();
            lineItemDTOs= LineItemMapper.getLineItems(lineItemService.findAllByInvoiceId(invoiceId));
            return lineItemDTOs;
        } catch (IllegalArgumentException | NotFoundException e) {
            throw new ResponseException(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            throw new ResponseException(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }



    @DeleteMapping("{id}")
    public void remove(@PathVariable Integer id) throws ResponseException {
        try {
            invoiceService.remove(id);
        } catch (NotFoundException | IllegalArgumentException e) {
            throw new ResponseException(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            throw new ResponseException(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
