package com.ucx.training.shop.service.integ;

import com.ucx.training.shop.entity.Address;
import com.ucx.training.shop.entity.Costumer;
import com.ucx.training.shop.exception.NotFoundException;
import com.ucx.training.shop.repository.CostumerRepository;
import com.ucx.training.shop.service.CostumerService;
import com.ucx.training.shop.type.RecordStatus;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CustomerServiceTests {

    @Autowired
    private CostumerService costumerService;
    @Autowired
    private CostumerRepository costumerRepository;

    private List<Costumer> costumers;
    private Costumer costumer;

    @Before
    public void setup() {
        costumers = new ArrayList<>();
        this.costumer = new Costumer();
        costumer.setName("Test-JehonAAAA");
        costumer.setAddresses(Arrays.asList(new Address("Rruga", 1000, "Prishtina", "Kosova", null)));
        costumer.setRecordStatus(RecordStatus.ACTIVE);

    }

    @After
    public void cleanup() {
        costumers.forEach(e -> costumerRepository.delete(e));
    }

    /**
     * This method tests customer creation
     * <p>
     * Customer cannot be created without at least 1 address!
     */
    @Test
    public void testCreate() {
        Costumer createdCostumer = costumerService.save(costumer);
        assertNotNull(createdCostumer);
        assertNotNull(createdCostumer.getId());
        Costumer foundCostumer = costumerService.findById(createdCostumer.getId());
        assertEquals(foundCostumer.getId(), createdCostumer.getId());
        costumers.add(createdCostumer);
    }

    @Transactional
    @Test
    public void testDelete() throws NotFoundException {
        Costumer createdCostumer = costumerService.save(costumer);
        assertNotNull(createdCostumer);
        assertNotNull(createdCostumer.getId());
        Costumer foundCostumer = costumerService.findById(createdCostumer.getId());
        costumerService.remove(foundCostumer.getId());
        assertEquals(RecordStatus.INACTIVE, foundCostumer.getRecordStatus());
        costumers.add(createdCostumer);
    }

    @Test
    public void testUpdate() throws NotFoundException {
        Costumer createdCostumer = costumerService.save(costumer);
        assertNotNull(createdCostumer);
        assertNotNull(createdCostumer.getId());
        Costumer foundCostumer = costumerService.findById(createdCostumer.getId());
        Costumer newCostumer = new Costumer();
        newCostumer.setName("JEHONAAAAA");
        Costumer updatedCostumer = costumerService.update(newCostumer, foundCostumer.getId());
        assertEquals("JEHONAAAAA", updatedCostumer.getName());
    }

    @Test
    public void WhenFindingCustomer_GivenValidId_ShouldReturnCustomer() {
        Costumer createdCostumer = costumerService.save(costumer);
        assertNotNull(createdCostumer);
        assertNotNull(createdCostumer.getId());
        Costumer foundCostumer = costumerService.findById(createdCostumer.getId());
        assertEquals(createdCostumer.getId(), foundCostumer.getId());
    }
}
