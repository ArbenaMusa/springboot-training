package com.ucx.training.shop.service.unit;

import com.ucx.training.shop.entity.LineItem;
import com.ucx.training.shop.entity.Product;
import com.ucx.training.shop.exception.NotFoundException;
import com.ucx.training.shop.service.BaseService;
import com.ucx.training.shop.type.RecordStatus;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LineItemServiceUnitTests {

    @Mock
    private BaseService baseService;

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

        LineItem createdLineItem = new LineItem();
        createdLineItem.setId(1);
        createdLineItem.setProduct(product);
        createdLineItem.setQuantity(3);

        when(baseService.save(createdLineItem)).thenReturn(createdLineItem);
        assertEquals(Integer.valueOf(1), createdLineItem.getId());

        //update test
        createdLineItem.setQuantity(1);
        when(baseService.update(createdLineItem, createdLineItem.getId())).thenReturn(createdLineItem);
        assertEquals(Integer.valueOf(1), createdLineItem.getQuantity());
    }

    @Test
    public void testDelete() throws NotFoundException {
        doThrow(IllegalArgumentException.class).when(baseService).remove(null);
        baseService.remove(13);
    }

    @Test(expected = NotFoundException.class)
    public void WhenRemovingEntity_GivenInvalidId_ShouldThrowNotFoundException() throws NotFoundException {
        doThrow(NotFoundException.class).when(baseService).remove(200);
    }

}
