package com.ucx.training.shop.controller;

import com.ucx.training.shop.entity.Invoice;
import com.ucx.training.shop.service.InvoiceService;
import com.ucx.training.shop.service.LineItemService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("invoices")
public class InvoiceController extends BaseController<Invoice, Integer> {

    private LineItemService lineItemService;
    private InvoiceService invoiceService;

    public InvoiceController(LineItemService lineItemService, InvoiceService invoiceService) {
        this.lineItemService = lineItemService;
        this.invoiceService = invoiceService;
    }
}
