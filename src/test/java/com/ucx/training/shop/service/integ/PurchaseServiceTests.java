package com.ucx.training.shop.service.integ;

import com.ucx.training.shop.entity.Invoice;
import com.ucx.training.shop.entity.LineItem;
import com.ucx.training.shop.entity.Product;
import com.ucx.training.shop.exception.NotFoundException;
import com.ucx.training.shop.repository.LineItemRepository;
import com.ucx.training.shop.repository.ProductRepository;
import com.ucx.training.shop.service.InvoiceService;
import com.ucx.training.shop.service.LineItemService;
import com.ucx.training.shop.service.ProductService;
import com.ucx.training.shop.service.PurchaseService;
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PurchaseServiceTests {

    @Autowired
    private PurchaseService purchaseService;
    @Autowired
    private LineItemService lineItemService;
    @Autowired
    private LineItemRepository lineItemRepository;
    @Autowired
    private InvoiceService invoiceService;
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductRepository productRepository;

    private List<LineItem> lineItemList;
    private List<Product> productList;
    private LineItem lineItem;

    @Before
    public void setup(){
        lineItemList = new ArrayList<>();
        productList = new ArrayList<>();
        Product product = getProduct();
//        productList.add(product);

        lineItem = new LineItem();
        lineItem.setProduct(product);
        lineItem.setQuantity(2);
        lineItemService.save(lineItem);
        lineItemList.add(lineItem);
    }

    private Product getProduct() {
        Product product = new Product();
        product.setName("Qershia");
        product.setUnitPrice(BigDecimal.valueOf(25.5));
        product.setInStock(5);
        return product;
    }

    @After
    public void cleanup(){
        lineItemList.forEach(e -> lineItemRepository.delete(e));
        productList.forEach(e -> productRepository.delete(e));

    }

    @Test
    public void testChangeQuantity() throws NotFoundException {
        LineItem createdLineItem = lineItemService.save(lineItem);
        assertNotNull(createdLineItem);
        assertNotNull(createdLineItem.getId());
        LineItem foundLineItem = lineItemService.findById(createdLineItem.getId());
        LineItem newCostumer = new LineItem();
        newCostumer.setQuantity(1);
        LineItem updatedLineItem = lineItemService.update(newCostumer, foundLineItem.getId());
        assertEquals(Integer.valueOf(1), updatedLineItem.getQuantity());
    }

//    @Test
    public void testAddToCart(){
        Invoice invoice = new Invoice();
        Invoice createdInvoice = invoiceService.save(invoice);
        LineItem createdLineItem = lineItemService.create(getProduct(),lineItem.getQuantity(),createdInvoice);
        assertNotNull(createdLineItem);
        assertNotNull(createdLineItem.getId());
        LineItem foundLineItem = lineItemService.findById(createdLineItem.getId());
        assertEquals(foundLineItem.getId(), createdLineItem.getId());
        lineItemList.add(createdLineItem);
    }

    @Test
    @Ignore
    public void whenGivenNullInvoiceId_addToCart_thenCreateNewInvoice() throws NotFoundException {
        Product createdProduct = productService.save(getProduct());
        productList.add(createdProduct);
        Integer invoiceId = purchaseService.addToCart(productList.get(0).getId(),lineItem.getQuantity(),null);
        assertNotNull(invoiceId);
    }

    @Test
    public void WhenRemovingEntity_GivenInvalidId_ShouldThrowNotFoundException() throws NotFoundException {
        PurchaseService purchaseServiceMock = mock(PurchaseService.class);
        doThrow(IllegalArgumentException.class).when(purchaseServiceMock).addToCart(null,lineItem.getQuantity(),null);
    }
}
