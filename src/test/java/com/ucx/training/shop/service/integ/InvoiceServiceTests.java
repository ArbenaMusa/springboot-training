package com.ucx.training.shop.service.integ;

import com.ucx.training.shop.entity.*;
import com.ucx.training.shop.repository.*;
import com.ucx.training.shop.service.CostumerService;
import com.ucx.training.shop.service.InvoiceService;
import com.ucx.training.shop.service.LineItemService;
import com.ucx.training.shop.service.ProductService;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Ignore
public class InvoiceServiceTests {

    @Autowired
    InvoiceService invoiceService;
    @Autowired
    ProductService productService;
    @Autowired
    CostumerService costumerService;
    @Autowired
    LineItemService lineItemService;

    Costumer costumer;
    List<LineItem> lineItemList;
    LineItem lineItem1, lineItem2;

    Product product1, product2;
    Invoice priorInvoice;
    Integer invoiceId;


    @Autowired
    LineItemRepository lineItemRepository;
    @Autowired
    CostumerRepository costumerRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    InvoiceRepository invoiceRepository;


    @Before
    public void setup() {
        costumer = costumerService.findById(1);
        lineItemList = new ArrayList<>();


        product1 = productService.findById(1);
        product2 = productService.findById(3);

        priorInvoice = invoiceService.save(new Invoice());


        lineItem1 = lineItemService.create(product1, 8, priorInvoice);
        lineItem2 = lineItemService.create(product2, 7, priorInvoice);

    }

    @After
    public void cleanup() {
        lineItemRepository.delete(lineItem1);
        lineItemRepository.delete(lineItem2);
        invoiceRepository.delete(priorInvoice);

    }


//    @Test
    public void testUpdate() {
        lineItemList.add(lineItem1);
        lineItemList.add(lineItem2);
        Invoice updatedInvoice = invoiceService.update(lineItemList, costumer, priorInvoice);
        Invoice testIfUpdatedInvoice = invoiceService.findById(updatedInvoice.getId());
        assertEquals(updatedInvoice.getId(), testIfUpdatedInvoice.getId());


        assertEquals(testIfUpdatedInvoice.getCostumer().getId(), costumer.getId());
        //
        // assertEquals(testIfUpdatedInvoice.getLineItemList(), lineItemList);

    }
}
