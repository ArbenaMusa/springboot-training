package com.ucx.training.shop;

import com.ucx.training.shop.entity.Address;
import com.ucx.training.shop.entity.Costumer;
import com.ucx.training.shop.service.CostumerService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CustomerServiceUnitTestSuite {

    @InjectMocks
    private CostumerService costumerService;

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
        costumer.setName("TestFilani");
        costumer.setAddresses(Arrays.asList(new Address("Rruga", 1000, "Prishtina", "Kosova", null)));

        when(costumerService.save(costumer)).thenReturn(createdCostumer);
        assertEquals(Long.valueOf(1), createdCostumer.getId());
    }
}
