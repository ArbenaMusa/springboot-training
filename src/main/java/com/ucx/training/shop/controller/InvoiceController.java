package com.ucx.training.shop.controller;

import com.ucx.training.shop.entity.Invoice;
import com.ucx.training.shop.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("shop/invoices")
public class InvoiceController {

    @Autowired
    private InvoiceService invoiceService;

    @PostMapping
    public Invoice create(@RequestBody Invoice invoice) {
        return invoiceService.save(invoice);
    }

    @GetMapping
    public List<Invoice> findAll() {
        return invoiceService.findAll();
    }

    @GetMapping("{id}")
    public Invoice findById(@PathVariable Integer id) {
        return invoiceService.findById(id);
    }

    @PutMapping("{id}")
    public Invoice update(@RequestBody Invoice invoice) {
        return invoiceService.update(invoice);
    }

    @DeleteMapping("{id}")
    public void remove(@PathVariable Integer id) {
        invoiceService.remove(id);
    }
}
