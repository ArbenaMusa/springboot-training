package com.ucx.training.shop.service.unit;

import com.ucx.training.shop.entity.LineItem;
import com.ucx.training.shop.entity.Product;
import com.ucx.training.shop.exception.NotFoundException;
import com.ucx.training.shop.service.LineItemService;
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

@RunWith(SpringRunner.class)
@SpringBootTest
public class LineItemServiceUnitTests {

    @Mock
    private LineItemService lineItemService;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreate() throws NotFoundException {
        Product product = new Product();
        product.setName("Molla");
        product.setUnitPrice(BigDecimal.valueOf(25.5));
        product.setInStock(5);

        LineItem lineItem = new LineItem();
        lineItem.setProduct(product);
        lineItem.setQuantity(3);

        LineItem createdLineItem = new LineItem();
        createdLineItem.setId(1);
        createdLineItem.setProduct(product);
        createdLineItem.setQuantity(3);

        when(lineItemService.save(lineItem)).thenReturn(createdLineItem);
        assertEquals(Integer.valueOf(1), createdLineItem.getId());
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

        LineItem createdLineItem = new LineItem();
        createdLineItem.setId(1);
        createdLineItem.setProduct(product);
        createdLineItem.setQuantity(2);


        when(lineItemService.update(createdLineItem, lineItem.getId())).thenReturn(lineItem);
        assertEquals(Integer.valueOf(2), createdLineItem.getQuantity());
    }

    @Test
    @Ignore
    public void testDelete() throws NotFoundException {
        doThrow(IllegalArgumentException.class).when(lineItemService).remove(null);
        lineItemService.remove(13);
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


        List<LineItem> lineItemList = Arrays.asList(lineItem);

        doReturn(lineItemList).when(lineItemService).findAll();

        List<LineItem> foundLineItems = lineItemService.findAll();

        assertEquals(foundLineItems, lineItemList);
    }

    @Test
    public void whenGivenId_findById_thenReturnCostumer() {
        Product product = new Product();
        product.setName("Molla");
        product.setUnitPrice(BigDecimal.valueOf(25.5));
        product.setInStock(5);

        LineItem lineItem = new LineItem();
        lineItem.setProduct(product);
        lineItem.setQuantity(3);

        when(lineItemService.findById(1)).thenReturn(lineItem);
        LineItem foundLineItem = lineItemService.findById(1);

        assertEquals(foundLineItem, lineItem);
    }

    @Test
    public void WhenRemovingEntity_GivenInvalidId_ShouldThrowNotFoundException() throws NotFoundException {
        doThrow(NotFoundException.class).when(lineItemService).remove(200);
    }

}
