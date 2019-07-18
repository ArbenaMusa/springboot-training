package com.ucx.training.shop.controller;

import com.ucx.training.shop.dto.InvoiceDTO;
import com.ucx.training.shop.dto.LineItemDTO;
import com.ucx.training.shop.entity.Invoice;
import com.ucx.training.shop.exception.NotFoundException;
import com.ucx.training.shop.service.InvoiceService;
import com.ucx.training.shop.service.LineItemService;
import com.ucx.training.shop.util.InvoiceUtil;
import lombok.extern.log4j.Log4j2;
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
    public InvoiceDTO create(@RequestBody Invoice invoice){
        return InvoiceUtil.getInvoice(invoiceService.save(invoice));
    }

    @PutMapping("{id}")
    public InvoiceDTO update(@RequestBody Invoice invoice,@PathVariable Integer id){
       Invoice updatedInvoice = invoiceService.update(invoice.getLineItemList(),invoice.getCostumer(),invoice);
       return InvoiceUtil.getInvoice(updatedInvoice);
    }

    @GetMapping("{id}")
    public InvoiceDTO findInvoiceById(@PathVariable Integer id){
        return InvoiceUtil.getInvoice(invoiceService.findById(id));
    }

    @GetMapping("/lineitems/{invoiceId}")
    public List<LineItemDTO> getLineItemsByInvoiceId(@PathVariable Integer invoiceId) {
        return lineItemService.findAllByInvoiceId(invoiceId);
    }

    @DeleteMapping("{id}")
    public void remove(@PathVariable Integer id){
        try {
            invoiceService.remove(id);
        }catch (NotFoundException e){
            log.error(e.getMessage());
        }
    }
}
