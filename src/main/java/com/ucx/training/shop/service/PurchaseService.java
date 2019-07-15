package com.ucx.training.shop.service;

import com.ucx.training.shop.entity.Costumer;
import com.ucx.training.shop.entity.Invoice;
import com.ucx.training.shop.entity.LineItem;
import com.ucx.training.shop.entity.Product;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Log4j2
@Service
@Transactional
public class PurchaseService {

    private LineItemService lineItemService;
    private InvoiceService invoiceService;
    private ProductService productService;
    private CostumerService costumerService;

    public PurchaseService(LineItemService lineItemService, InvoiceService invoiceService, ProductService productService, CostumerService costumerService) {
        this.lineItemService = lineItemService;
        this.invoiceService = invoiceService;
        this.productService = productService;
        this.costumerService = costumerService;
    }

    public Integer addToCart(Integer productId, Integer quantity, Integer invoiceId) {
        Integer newInvoiceId;
        Product foundProduct = productService.findById(productId);
        if (foundProduct == null) throw new RuntimeException("Product could not be found!");
        if (quantity == null) throw new IllegalArgumentException("No quantity provided");
        if(foundProduct.getInStock() < quantity) throw new RuntimeException("Out of stock!");

        if (invoiceId == null) {
            Invoice invoice = new Invoice();
            Invoice createdInvoice = invoiceService.save(invoice);
            lineItemService.create(foundProduct, quantity, createdInvoice);
            newInvoiceId = createdInvoice.getId();
        } else {
            Invoice foundInvoice = invoiceService.findById(invoiceId);
            if (foundInvoice == null) throw new RuntimeException("No such Invoice found");
            lineItemService.create(foundProduct, quantity, foundInvoice);
            newInvoiceId = invoiceId;
        }

        return invoiceId;
    }

    public Invoice buy(Integer costumerId, Integer invoiceId) {
        Invoice foundInvoice = invoiceService.findById(invoiceId);
        if (foundInvoice == null) throw new RuntimeException("No such Invoice found" + invoiceId);
        Costumer foundCostumer = costumerService.findById(costumerId);
        if (foundCostumer == null) throw new RuntimeException("No such Costumer found" + costumerId);
        List<LineItem> lineItemList = lineItemService.findAllByInvoice(foundInvoice);
        Invoice generatedInvoice = invoiceService.update(lineItemList, foundCostumer, foundInvoice);
        Invoice printedInvoice = invoiceService.print(generatedInvoice.getId());
        return printedInvoice;
    }
}
