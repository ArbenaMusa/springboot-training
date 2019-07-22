package com.ucx.training.shop.service.integ;

import com.ucx.training.shop.entity.LineItem;
import com.ucx.training.shop.entity.Product;
import com.ucx.training.shop.exception.NotFoundException;
import com.ucx.training.shop.repository.LineItemRepository;
import com.ucx.training.shop.service.LineItemService;
import com.ucx.training.shop.type.RecordStatus;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;

import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LineItemServiceTests {

    @Autowired
    private LineItemService lineItemService;
    @Autowired
    private LineItemRepository lineItemRepository;

    private List<LineItem> lineItemList;

    @Before
    public void setup(){
        lineItemList = new ArrayList<>();
        /*Product product = new Product();
        product.setName("Pjeshka");
        product.setUnitPrice(BigDecimal.valueOf(25.5));
        product.setInStock(5);

        LineItem lineItem = new LineItem();
        lineItem.setProduct(product);
        lineItem.setQuantity(2);
        lineItemService.save(lineItem);
        lineItemList.add(lineItem);*/
    }

    @After
    public void cleanup(){
        lineItemList.forEach(e -> lineItemRepository.delete(e));
    }

    @Test
    @Transactional
    public void testCreate() throws NotFoundException {
        Product product = new Product();
        product.setName("Pjeshka");
        product.setUnitPrice(BigDecimal.valueOf(25.5));
        product.setInStock(5);

        LineItem lineItem = new LineItem();
        lineItem.setProduct(product);
        lineItem.setQuantity(2);

        LineItem createdLineItem = lineItemService.save(lineItem);
        assertNotNull(createdLineItem);
        assertNotNull(createdLineItem.getId());

        /*LineItem foundLineItem = lineItemService.findById(createdLineItem.getId());
        assertEquals(foundLineItem.getId(), createdLineItem.getId());*/
        lineItemList.add(createdLineItem);
    }

    //TODO: Is not working
//    @Test
//    public void testUpdate() throws NotFoundException {
//        LineItem lineItem = lineItemService.findById(5);
//        lineItem.setQuantity(1);
//        lineItemService.update(lineItem,5);
//    }


    @Test
    @Transactional
    @Ignore
    public void testDelete() throws NotFoundException {
        lineItemList.forEach(e -> System.out.println(e));
        lineItemService.remove(lineItemList.get(0).getId());
        LineItem foundLineItem = lineItemService.findById(lineItemList.get(0).getId());
        assertEquals(RecordStatus.INACTIVE, lineItemList.get(0).getRecordStatus());
    }
}
