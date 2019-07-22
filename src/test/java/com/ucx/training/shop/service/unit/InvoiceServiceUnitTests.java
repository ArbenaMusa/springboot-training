package com.ucx.training.shop.service.unit;

import com.ucx.training.shop.entity.*;
import com.ucx.training.shop.exception.NotFoundException;
import com.ucx.training.shop.service.InvoiceService;
import com.ucx.training.shop.type.RecordStatus;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@RunWith(SpringRunner.class)
@SpringBootTest
public class InvoiceServiceUnitTests {

    @Mock
    private InvoiceService invoiceService;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreate() {
        Product product = new Product();
        product.setName("Molla");
        product.setUnitPrice(BigDecimal.valueOf(25.5));
        product.setInStock(5);

        LineItem lineItem = new LineItem();
        lineItem.setProduct(product);
        lineItem.setQuantity(3);

        Costumer costumer = new Costumer();
        costumer.setName("TestFilani");
        costumer.setAddresses(Arrays.asList(new Address("Rruga", 1000, "Prishtina", "Kosova", null)));
        costumer.setRecordStatus(RecordStatus.ACTIVE);

        Invoice invoice = new Invoice();
        invoice.setCostumer(costumer);
        invoice.setLineItemList(Arrays.asList(lineItem));

        Invoice createdInvoice = new Invoice();
        createdInvoice.setId(1);
        createdInvoice.setCostumer(costumer);
        createdInvoice.setLineItemList(Arrays.asList(lineItem));

        when(invoiceService.save(invoice)).thenReturn(createdInvoice);
        assertEquals(Integer.valueOf(1), createdInvoice.getId());
    }

    @Test
    @Ignore
    public void testDelete() throws NotFoundException {
        Costumer costumer = new Costumer();
        costumer.setName("TestFilani");
        costumer.setAddresses(Arrays.asList(new Address("Rruga", 1000, "Prishtina", "Kosova", null)));

        Product product = new Product();
        product.setName("Molla");
        product.setUnitPrice(BigDecimal.valueOf(25.5));
        product.setInStock(5);

        LineItem lineItem = new LineItem();
        lineItem.setProduct(product);
        lineItem.setQuantity(3);

        Invoice invoice = new Invoice();
        invoice.setCostumer(costumer);
        invoice.setLineItemList(Arrays.asList(lineItem));

        doNothing().when(invoiceService).remove(invoice.getId());
        invoiceService.remove(1);

        verify(invoiceService, times(1)).remove(1);
        assertEquals(RecordStatus.INACTIVE, invoice.getRecordStatus());
    }

    @Test
    public void testUpdate() throws NotFoundException {
        Product product = new Product();
        product.setName("Molla");
        product.setUnitPrice(BigDecimal.valueOf(25.5));
        product.setInStock(5);

        LineItem lineItem = new LineItem();
        lineItem.setProduct(product);
        lineItem.setQuantity(3);

        Costumer costumer = new Costumer();
        costumer.setName("TestFilani");
        costumer.setAddresses(Arrays.asList(new Address("Rruga", 1000, "Prishtina", "Kosova", null)));
        costumer.setRecordStatus(RecordStatus.ACTIVE);

        Invoice invoice = new Invoice();
        invoice.setCostumer(costumer);
        invoice.setLineItemList(Arrays.asList(lineItem));

        Costumer newCostumer = new Costumer();
        newCostumer.setName("testtest");
        newCostumer.setAddresses(Arrays.asList(new Address("Rruga", 1000, "Prishtina", "Kosova", null)));
        newCostumer.setRecordStatus(RecordStatus.ACTIVE);

        Invoice createdInvoice = new Invoice();
        createdInvoice.setId(1);
        createdInvoice.setCostumer(newCostumer);
        createdInvoice.setLineItemList(Arrays.asList(lineItem));

        when(invoiceService.update(createdInvoice, invoice.getId())).thenReturn(invoice);
        assertEquals(newCostumer, createdInvoice.getCostumer());
    }

    @Test
    public void whenFindAll_thenReturnCostumerList() {
        Product product = new Product();
        product.setName("Molla");
        product.setUnitPrice(BigDecimal.valueOf(25.5));
        product.setInStock(5);

        LineItem lineItem = new LineItem();
        lineItem.setProduct(product);
        lineItem.setQuantity(3);

        Costumer costumer = new Costumer();
        costumer.setName("TestFilani");
        costumer.setAddresses(Arrays.asList(new Address("Rruga", 1000, "Prishtina", "Kosova", null)));
        costumer.setRecordStatus(RecordStatus.ACTIVE);

        Invoice invoice = new Invoice();
        invoice.setCostumer(costumer);
        invoice.setLineItemList(Arrays.asList(lineItem));

        List<Invoice> invoiceList = Arrays.asList(invoice);
        doReturn(invoiceList).when(invoiceService).findAll();
        List<Invoice> foundInvoice = invoiceService.findAll();
        assertEquals(foundInvoice, invoiceList);
    }

    @Test
    public void whenGivenId_findById_thenReturnInvoice() {
        Product product = new Product();
        product.setName("Molla");
        product.setUnitPrice(BigDecimal.valueOf(25.5));
        product.setInStock(5);

        LineItem lineItem = new LineItem();
        lineItem.setProduct(product);
        lineItem.setQuantity(3);

        Costumer costumer = new Costumer();
        costumer.setName("TestFilani");
        costumer.setAddresses(Arrays.asList(new Address("Rruga", 1000, "Prishtina", "Kosova", null)));
        costumer.setRecordStatus(RecordStatus.ACTIVE);

        Invoice invoice = new Invoice();
        invoice.setCostumer(costumer);
        invoice.setLineItemList(Arrays.asList(lineItem));

        when(invoiceService.findById(1)).thenReturn(invoice);
        Invoice foundInvoice = invoiceService.findById(1);
        assertEquals(foundInvoice, invoice);
    }
}
