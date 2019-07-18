package com.ucx.training.shop.service;

import com.ucx.training.shop.entity.Costumer;
import com.ucx.training.shop.entity.Invoice;
import com.ucx.training.shop.entity.LineItem;
import com.ucx.training.shop.repository.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional
public class InvoiceService extends BaseService<Invoice, Integer> {

    @Autowired
    private InvoiceRepository invoiceRepository;

    public Invoice update(List<LineItem> lineItemList, Costumer costumer, Invoice invoice) {
        if (lineItemList == null || lineItemList.isEmpty()) {
            throw new IllegalArgumentException("Cannot print Invoice, list is missing");
        }
        if (costumer == null) {
            throw new IllegalArgumentException("Cannot print Invoice, Costumer is missing");
        }
        if (invoice == null) {
            throw new IllegalArgumentException("Invoice must not be null");
        }
        BigDecimal total = lineItemList
                .stream()
                .map(e -> e.getProduct().getUnitPrice().multiply(BigDecimal.valueOf(e.getQuantity())))
                .reduce(BigDecimal.ZERO, (a, b) -> a.add(b));
        invoice.setInvoiceNumber((int) (Math.random() * 100 + 1));
        invoice.setLineItemList(lineItemList);
        invoice.setCostumer(costumer);
        invoice.setTotal(total);
        return invoice;
    }

    public Invoice print(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("Invalid argument: " + id);
        }
        return findById(id);
    }

    public List<Invoice> findAllByCostumer(Costumer costumer) {
        if (costumer == null) {
            throw new IllegalArgumentException("Invalid argument: " + costumer);
        }

        return invoiceRepository.findAllByCostumer(costumer);
    }




}
