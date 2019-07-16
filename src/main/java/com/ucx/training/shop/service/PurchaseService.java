package com.ucx.training.shop.service;

import com.ucx.training.shop.entity.Costumer;
import com.ucx.training.shop.entity.Invoice;
import com.ucx.training.shop.entity.LineItem;
import com.ucx.training.shop.entity.Product;
import com.ucx.training.shop.type.RecordStatus;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

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
        if (foundProduct.getInStock() < quantity) throw new RuntimeException("Out of stock!");

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

        return newInvoiceId;
    }

    public Invoice buy(Integer costumerId, Integer invoiceId) {
        Invoice foundInvoice = invoiceService.findById(invoiceId);
        if (foundInvoice == null) throw new RuntimeException("No such Invoice found" + invoiceId);
        Costumer foundCostumer = costumerService.findById(costumerId);
        if (foundCostumer == null) throw new RuntimeException("No such Costumer found" + costumerId);
        List<LineItem> lineItemList = lineItemService.findAllByInvoice(foundInvoice);
        Invoice generatedInvoice = invoiceService.update(lineItemList, foundCostumer, foundInvoice);
        Invoice printedInvoice = invoiceService.print(generatedInvoice.getId());

        reduceStock(lineItemList);
        return printedInvoice;
    }

    //Reduces stock of Product from quantity given
    private void reduceStock(List<LineItem> lineItemList) {
        lineItemList.forEach(lineItem -> {
            Product product = lineItem.getProduct();
            product.setInStock(product.getInStock() - lineItem.getQuantity());
        });
    }

    public LineItem cancelLineItem(Integer lineItemId) {
        if (lineItemId == null) {
            throw new IllegalArgumentException("Invalid argument");
        }
        LineItem foundLineItem = lineItemService.findById(lineItemId);
        if (foundLineItem == null) {
            throw new RuntimeException("LineItem does not exist");
        }
        foundLineItem.setRecordStatus(RecordStatus.INACTIVE);
        //foundLineItem.setInvoice(null);
        //lineItemService.update(foundLineItem, lineItemId);
        return foundLineItem;
    }

    public Invoice cancelPurchase(Integer invoiceId) {
        if (invoiceId == null) throw new IllegalArgumentException("Invalid argument" + invoiceId);
        Invoice foundInvoice = invoiceService.findById(invoiceId);
        if (foundInvoice == null) throw new RuntimeException("Invoice does not exist" + invoiceId);
        if (!foundInvoice.getLineItemList().isEmpty()) {
            foundInvoice.getLineItemList().forEach(lineItem -> {
                cancelLineItem(lineItem.getId());
            });
        }
        foundInvoice.setRecordStatus(RecordStatus.INACTIVE);
        //foundInvoice.setCostumer(null);
        //update(foundInvoice, invoiceId);
        return foundInvoice;
    }
}
