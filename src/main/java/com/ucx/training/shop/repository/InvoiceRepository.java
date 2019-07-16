package com.ucx.training.shop.repository;

import com.ucx.training.shop.entity.Costumer;
import com.ucx.training.shop.entity.Invoice;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvoiceRepository extends BaseRepository<Invoice, Integer> {
    List<Invoice> findAllByCostumer(Costumer costumer);
}
