package com.ucx.training.shop.service.unit;

import com.ucx.training.shop.entity.LineItem;
import com.ucx.training.shop.entity.Product;
import com.ucx.training.shop.service.BaseService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LineItemServiceUnitTests {

    @Mock
    private BaseService baseService;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreate(){
        Product product = new Product();
        product.setName("Pjeshka");
        product.setUnitPrice(BigDecimal.valueOf(25.5));
        product.setInStock(5);

        LineItem lineItem = new LineItem();
        lineItem.setProduct(product);
        lineItem.setQuantity(2);

        LineItem createdLineItem = new LineItem();
        createdLineItem.setId(1);
        createdLineItem.setProduct(product);
        createdLineItem.setQuantity(3);

        when(baseService.save(lineItem)).thenReturn(createdLineItem);
        assertEquals(Integer.valueOf(1), createdLineItem.getId());
    }
}
