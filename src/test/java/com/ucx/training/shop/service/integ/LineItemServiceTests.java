package com.ucx.training.shop.service.integ;

import com.ucx.training.shop.entity.LineItem;
import com.ucx.training.shop.entity.Product;
import com.ucx.training.shop.exception.NotFoundException;
import com.ucx.training.shop.repository.LineItemRepository;
import com.ucx.training.shop.service.LineItemService;
import com.ucx.training.shop.type.RecordStatus;
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
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LineItemServiceTests {

    @Autowired
    private LineItemService lineItemService;
    @Autowired
    private LineItemRepository lineItemRepository;

    private List<LineItem> lineItemList;
    private LineItem lineItem;

    @Before
    public void setup(){
        lineItemList = new ArrayList<>();
        Product product = getProduct();
        lineItem = new LineItem();
        lineItem.setProduct(product);
        lineItem.setQuantity(2);
        lineItemService.save(lineItem);
        lineItemList.add(lineItem);
    }

    private Product getProduct() {
        Product product = new Product();
        product.setName("Test-Produkt");
        product.setUnitPrice(BigDecimal.valueOf(25.5));
        product.setInStock(5);
        return product;
    }

    @After
    public void cleanup(){
        lineItemList.forEach(e -> lineItemRepository.delete(e));
    }

    @Test
    public void testCreate() {
        LineItem createdLineItem = lineItemService.save(lineItem);
        assertNotNull(createdLineItem);
        assertNotNull(createdLineItem.getId());
        LineItem foundLineItem = lineItemService.findById(createdLineItem.getId());
        assertEquals(foundLineItem.getId(), createdLineItem.getId());
        lineItemList.add(createdLineItem);
    }

    @Test
    public void testUpdate() throws NotFoundException {
        LineItem createdLineItem = lineItemService.save(lineItem);
        assertNotNull(createdLineItem);
        assertNotNull(createdLineItem.getId());
        LineItem foundLineItem = lineItemService.findById(createdLineItem.getId());
        LineItem newCostumer = new LineItem();
        newCostumer.setQuantity(2);
        LineItem updatedLineItem = lineItemService.update(newCostumer, foundLineItem.getId());
        assertEquals(Integer.valueOf(2), updatedLineItem.getQuantity());
    }

    @Test
    @Transactional
    public void testDelete() throws NotFoundException {
        LineItem createdLineItem = lineItemService.save(lineItem);
        assertNotNull(createdLineItem);
        assertNotNull(createdLineItem.getId());
        LineItem foundLineItem = lineItemService.findById(createdLineItem.getId());
        lineItemService.remove(foundLineItem.getId());
        assertEquals(RecordStatus.INACTIVE, foundLineItem.getRecordStatus());
        lineItemList.add(createdLineItem);
    }

    @Test
    public void WhenFindingLineItem_GivenValidId_ShouldReturnLineItem() {
        LineItem createdLineItem = lineItemService.save(lineItem);
        assertNotNull(createdLineItem);
        assertNotNull(createdLineItem.getId());
        LineItem foundLineItem = lineItemService.findById(createdLineItem.getId());
        assertEquals(createdLineItem.getId(), foundLineItem.getId());
    }
}
