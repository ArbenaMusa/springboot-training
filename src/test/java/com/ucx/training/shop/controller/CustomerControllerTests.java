package com.ucx.training.shop.controller;

import com.ucx.training.shop.dto.CustomerDTO;
import com.ucx.training.shop.entity.Address;
import com.ucx.training.shop.entity.Costumer;
import com.ucx.training.shop.repository.CostumerRepository;
import com.ucx.training.shop.service.CostumerService;
import lombok.extern.log4j.Log4j2;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertNotNull;

@Log4j2
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CustomerControllerTests {

    private List<Integer> customerList;

    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private CostumerService customerService;
    @Autowired
    private CostumerRepository costumerRepository;

    @Before
    public void setUp() {
        customerList = new ArrayList<>();

    }

    @After
    public void cleanUp() {
        customerList.forEach(e -> {
            try {
                costumerRepository.delete(customerService.findById(e));
            } catch (Exception ex) {
                log.error(ex.getMessage());
            }
        });
    }

    @Test
    public void testSave() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        Costumer customer = new Costumer();
        customer.setName("testName");
        customer.setAddresses(Arrays.asList(new Address("Rruga", 1000, "Prishtina", "Kosova", null)));

        HttpEntity<Costumer> entity = new HttpEntity<>(customer, headers);

        CustomerDTO savedCustomer = restTemplate.exchange("/costumers", HttpMethod.POST, entity, CustomerDTO.class).getBody();

        assertNotNull(savedCustomer);
        assertNotNull(savedCustomer.getId());
        customerList.add(savedCustomer.getId());
    }
}
