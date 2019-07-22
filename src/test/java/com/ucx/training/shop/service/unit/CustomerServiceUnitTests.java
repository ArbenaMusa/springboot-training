package com.ucx.training.shop.service.unit;

import com.ucx.training.shop.entity.Address;
import com.ucx.training.shop.entity.Costumer;
import com.ucx.training.shop.exception.NotFoundException;
import com.ucx.training.shop.service.CostumerService;
import com.ucx.training.shop.type.RecordStatus;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CustomerServiceUnitTests {

    @Mock
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
        createdCostumer.setName("TestFilani");
        createdCostumer.setAddresses(Arrays.asList(new Address("Rruga", 1000, "Prishtina", "Kosova", null)));

        when(costumerService.save(costumer)).thenReturn(createdCostumer);
        assertEquals(Integer.valueOf(1), createdCostumer.getId());
    }

    @Test
    public void testDelete() throws NotFoundException {
        Costumer costumer = new Costumer();
        costumer.setName("TestFilani");
        costumer.setAddresses(Arrays.asList(new Address("Rruga", 1000, "Prishtina", "Kosova", null)));

        doNothing().when(costumerService).remove(costumer.getId());
        costumerService.remove(1);

        verify(costumerService, times(1)).remove(1);
        assertEquals(RecordStatus.INACTIVE, costumer.getRecordStatus());
    }

    @Test
    public void testUpdate() throws NotFoundException {
        Costumer costumer = new Costumer();
        costumer.setName("Pashtrik");
        costumer.setAddresses(Arrays.asList(new Address("Rruga", 1000, "Prishtina", "Kosova", null)));
        costumer.setRecordStatus(RecordStatus.ACTIVE);

        Costumer createdCostumer = new Costumer();
        createdCostumer.setId(1);
        createdCostumer.setName("Jehona");
        createdCostumer.setAddresses(Arrays.asList(new Address("Rruga", 1000, "Prishtina", "Kosova", null)));

        when(costumerService.update(createdCostumer, costumer.getId())).thenReturn(costumer);
        assertEquals("Jehona", createdCostumer.getName());
    }

    @Test
    public void whenFindAll_thenReturnCostumerList() {
        Costumer costumer = new Costumer();
        costumer.setName("TestFilani");
        costumer.setAddresses(Arrays.asList(new Address("Rruga", 1000, "Prishtina", "Kosova", null)));
        costumer.setRecordStatus(RecordStatus.ACTIVE);

        List<Costumer> costumerList = Arrays.asList(costumer);

        doReturn(costumerList).when(costumerService).findAll();

        List<Costumer> foundCostumers = costumerService.findAll();

        assertEquals(foundCostumers, costumerList);
    }

    @Test
    public void whenGivenId_findById_thenReturnCostumer() {
        Costumer costumer = new Costumer();
        costumer.setName("TestFilani");
        costumer.setAddresses(Arrays.asList(new Address("Rruga", 1000, "Prishtina", "Kosova", null)));
        costumer.setRecordStatus(RecordStatus.ACTIVE);

        when(costumerService.findById(1)).thenReturn(costumer);
        Costumer foundCostumer = costumerService.findById(1);

        assertEquals(foundCostumer, costumer);
    }
}
