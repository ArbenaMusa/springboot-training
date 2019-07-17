package com.ucx.training.shop.service;

import com.ucx.training.shop.entity.Costumer;
import com.ucx.training.shop.entity.Invoice;
import com.ucx.training.shop.entity.LineItem;
import com.ucx.training.shop.entity.Product;
import com.ucx.training.shop.exception.NotFoundException;
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
    private EmailService emailService;

    public PurchaseService(LineItemService lineItemService, InvoiceService invoiceService, ProductService productService, CostumerService costumerService, EmailService emailService) {
        this.lineItemService = lineItemService;
        this.invoiceService = invoiceService;
        this.productService = productService;
        this.costumerService = costumerService;
        this.emailService = emailService;
    }

    public Integer addToCart(Integer productId, Integer quantity, Integer invoiceId) throws NotFoundException {
        Integer newInvoiceId;
        Product foundProduct = productService.findById(productId);
        if (foundProduct == null) throw new NotFoundException("Product could not be found!");
        if (foundProduct.getRecordStatus() == RecordStatus.INACTIVE)
            throw new IllegalArgumentException("Product is deleted! " + foundProduct);
        if (quantity == null) throw new IllegalArgumentException("No quantity provided!");
        if (foundProduct.getInStock() < quantity) throw new NotFoundException("Out of stock!");

        if (invoiceId == null) {
            Invoice invoice = new Invoice();
            Invoice createdInvoice = invoiceService.save(invoice);
            lineItemService.create(foundProduct, quantity, createdInvoice);
            newInvoiceId = createdInvoice.getId();
        } else {
            Invoice foundInvoice = invoiceService.findById(invoiceId);
            if (foundInvoice == null) throw new RuntimeException("No such Invoice found");
            if (foundInvoice.getRecordStatus() == RecordStatus.INACTIVE)
                throw new IllegalArgumentException("Invoice is deleted! " + foundInvoice);
            lineItemService.create(foundProduct, quantity, foundInvoice);
            newInvoiceId = invoiceId;
        }

        return newInvoiceId;
    }

    public Invoice buy(Integer costumerId, Integer invoiceId) throws NotFoundException {
        Invoice foundInvoice = invoiceService.findById(invoiceId);
        if (foundInvoice == null) throw new NotFoundException("No such Invoice found" + invoiceId);
        if (foundInvoice.getRecordStatus() == RecordStatus.INACTIVE)
            throw new IllegalArgumentException("Invoice is deleted: " + foundInvoice);
        Costumer foundCostumer = costumerService.findById(costumerId);
        if (foundCostumer == null) throw new NotFoundException("No such Costumer found" + costumerId);
        if (foundCostumer.getRecordStatus() == RecordStatus.INACTIVE)
            throw new IllegalArgumentException("Costumer is deleted: " + foundCostumer);
        List<LineItem> lineItemList = lineItemService.findAllByInvoiceAndRecordStatusActive(foundInvoice);
        Invoice generatedInvoice = invoiceService.update(lineItemList, foundCostumer, foundInvoice);
        Invoice printedInvoice = invoiceService.print(generatedInvoice.getId());
        reduceStock(lineItemList);

        try {
            emailService.sendMail(foundCostumer, printedInvoice);
        }
        catch (Exception e)
        {

        }
        return printedInvoice;
    }

    //Reduces stock of Product from quantity given
    private void reduceStock(List<LineItem> lineItemList) {
        lineItemList.forEach(lineItem -> {
            Product product = lineItem.getProduct();
            product.setInStock(product.getInStock() - lineItem.getQuantity());
        });
    }

    public LineItem cancelLineItem(Integer lineItemId) throws NotFoundException{
        if (lineItemId == null) {
            throw new IllegalArgumentException("Invalid argument");
        }
        LineItem foundLineItem = lineItemService.findById(lineItemId);
        if (foundLineItem == null) {
            throw new NotFoundException("LineItem does not exist");
        }
        foundLineItem.setRecordStatus(RecordStatus.INACTIVE);
        return foundLineItem;
    }

    public Invoice cancelPurchase(Integer invoiceId) throws NotFoundException{
        if (invoiceId == null) throw new IllegalArgumentException("Invalid argument" + invoiceId);
        Invoice foundInvoice = invoiceService.findById(invoiceId);
        if (foundInvoice == null) throw new NotFoundException("Invoice does not exist" + invoiceId);
        if (!foundInvoice.getLineItemList().isEmpty()) {
            foundInvoice.getLineItemList().forEach(lineItem -> {
                try {
                    cancelLineItem(lineItem.getId());
                } catch (NotFoundException e) {
                    log.error(e.getMessage());
                }
            });
        }
        foundInvoice.setRecordStatus(RecordStatus.INACTIVE);
        return foundInvoice;
    }

    public LineItem changeQuantity(LineItem lineItem, Integer lineItemId) throws NotFoundException {
        if (lineItemId == null) {
            throw new IllegalArgumentException("Invalid argument: " + lineItemId);
        }
        LineItem foundLineItem = lineItemService.findById(lineItemId);
        if (foundLineItem == null) {
            throw new NotFoundException("Line item does not exist");
        }
        if (lineItem.getQuantity().equals(0)) {
            return cancelLineItem(lineItemId);
        }
        foundLineItem.setQuantity(lineItem.getQuantity());
        return foundLineItem;
    }
}
