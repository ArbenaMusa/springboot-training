package com.ucx.training.shop.service.integ;

import com.ucx.training.shop.entity.Address;
import com.ucx.training.shop.entity.Costumer;
import com.ucx.training.shop.repository.BaseRepository;
import com.ucx.training.shop.repository.CostumerRepository;
import com.ucx.training.shop.service.CostumerService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CustomerServiceTests {

    @Autowired
    private CostumerService costumerService;

    @Autowired
    private CostumerRepository costumerRepository;

    private List<Costumer> costumers;

    @Before
    public void setup(){
        costumers = new ArrayList<>();
    }

    @After
    public void cleanup(){
        costumers.forEach(e -> costumerRepository.delete(e));
    }

    /**
     * This method tests customer creation
     *
     * Customer cannot be created without at least 1 address!
     */
    @Test
    public void testCreate() {
        Costumer costumer = new Costumer();
        costumer.setName("TestFilani");
        costumer.setAddresses(Arrays.asList(new Address("Rruga", 1000, "Prishtina", "Kosova", null)));
        Costumer createdCostumer = costumerService.save(costumer);
        assertNotNull(createdCostumer);
        assertNotNull(createdCostumer.getId());
        Costumer foundCostumer = costumerService.findById(createdCostumer.getId());
        assertEquals(foundCostumer.getId(), createdCostumer.getId());
        costumers.add(createdCostumer);
    }
}
