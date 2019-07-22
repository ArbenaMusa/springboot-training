package com.ucx.training.shop.service.unit;

import com.ucx.training.shop.entity.Address;
import com.ucx.training.shop.entity.Costumer;
import com.ucx.training.shop.service.BaseService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CustomerServiceUnitTests {

    @Mock
    private BaseService baseService;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreate() {
        Costumer costumer = new Costumer();
        costumer.setName("TestFilani");
        costumer.setAddresses(Arrays.asList(new Address("Rruga", 1000, "Prishtina", "Kosova", null)));

        Costumer createdCostumer = new Costumer();
        createdCostumer.setId(1);
        createdCostumer.setName("TestFilani");
        createdCostumer.setAddresses(Arrays.asList(new Address("Rruga", 1000, "Prishtina", "Kosova", null)));

        when(baseService.save(costumer)).thenReturn(createdCostumer);
        assertEquals(Integer.valueOf(1), createdCostumer.getId());
    }
}
