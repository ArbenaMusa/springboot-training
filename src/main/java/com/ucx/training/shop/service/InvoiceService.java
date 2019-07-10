package com.ucx.training.shop.service;

import com.ucx.training.shop.entity.Invoice;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class InvoiceService extends BaseService<Invoice,Integer> {
}
