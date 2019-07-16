package com.ucx.training.shop.controller;

import com.ucx.training.shop.entity.Invoice;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("invoices")
public class InvoiceController extends BaseController<Invoice, Integer> {

}
