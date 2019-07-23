package com.ucx.training.shop.service.integ;

import com.ucx.training.shop.entity.*;
import com.ucx.training.shop.repository.*;
import com.ucx.training.shop.service.CostumerService;
import com.ucx.training.shop.service.InvoiceService;
import com.ucx.training.shop.service.LineItemService;
import com.ucx.training.shop.service.ProductService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
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
    LineItem lineItem1;

    Product product1;
    Invoice priorInvoice;


    @Autowired
    CostumerRepository costumerRepository;
    @Autowired
    LineItemRepository lineItemRepository;
    @Autowired
    InvoiceRepository invoiceRepository;
    @Autowired
    ProductRepository productRepository;


    @Before
    public void setup() {


        costumer = new Costumer();
        costumer.setName("agron");
        costumer.setEmail("agron11@gmail.com");
        costumer.setAddresses(Arrays.asList(new Address()));
        costumerService.save(costumer);

        lineItemList = new ArrayList<>();

        priorInvoice = invoiceService.save(new Invoice());

        product1 = new Product();
        product1.setName("Schauma");
        product1.setUnitPrice(BigDecimal.valueOf(3));
        product1.setInStock(700);
        productService.save(product1);

        lineItem1 = lineItemService.create(product1, 8, priorInvoice);
        lineItemList.add(lineItem1);
    }

    @After
    public void cleanup() {
        invoiceRepository.delete(priorInvoice);
        lineItemList.forEach((e)->{lineItemRepository.delete(e);});
        costumerRepository.delete(costumer);
        productRepository.delete(product1);
    }


    @Test
    public void testUpdate() {
        Invoice updatedInvoice = invoiceService.update(lineItemList, costumer, priorInvoice);
        Invoice testIfUpdatedInvoice = invoiceService.findById(updatedInvoice.getId());
        assertEquals(updatedInvoice.getId(), testIfUpdatedInvoice.getId());
        assertEquals(testIfUpdatedInvoice.getCostumer().getId(), costumer.getId());
        assertEquals(testIfUpdatedInvoice.getTotal(), updatedInvoice.getTotal());


    }






    Invoice invoiceToBePrinted;

    @Before
    public void printSetup(){

        invoiceToBePrinted=new Invoice();

    }

    @Test
    public void testPrint(){
        Invoice updatedInvoice = invoiceService.update(lineItemList, costumer, priorInvoice);
        Invoice testInvoice1=invoiceService.findById(priorInvoice.getId());
      assertTrue(testInvoice1.getId().equals(invoiceService.print(priorInvoice.getId()).getId()));
    }



}
