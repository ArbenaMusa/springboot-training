package com.ucx.training.shop.service;

import com.ucx.training.shop.entity.Costumer;
import com.ucx.training.shop.entity.Invoice;
import com.ucx.training.shop.repository.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class InvoiceService extends BaseService<Invoice,Integer> {

    @Autowired
    private InvoiceRepository invoiceRepository;

    public List<Invoice> findAllByCostumer(Costumer costumer) {
        if (costumer == null) {
            throw new IllegalArgumentException("Invalid argument: " + costumer);
        }

        return invoiceRepository.findAllByCostumer(costumer);
    }
}
