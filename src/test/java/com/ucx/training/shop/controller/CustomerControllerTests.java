package com.ucx.training.shop.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ucx.training.shop.dto.CustomerDTO;
import com.ucx.training.shop.entity.Address;
import com.ucx.training.shop.entity.Costumer;
import com.ucx.training.shop.repository.CostumerRepository;
import com.ucx.training.shop.service.CostumerService;
import com.ucx.training.shop.type.RecordStatus;
import lombok.extern.log4j.Log4j2;
import net.minidev.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@Log4j2
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CustomerControllerTests {

    private List<Costumer> customerList;
    private Costumer customer;

    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private CostumerService customerService;
    @Autowired
    private CostumerRepository costumerRepository;

    @Before
    public void setUp() {
        customerList = new ArrayList<>();
        this.customer = new Costumer();
        customer.setName("Costumer-Test");
        customer.setAddresses(Arrays.asList(new Address("Rruga", 1000, "Prishtina", "Kosova", null)));

    }

    @After
    public void cleanUp() {
        customerList.forEach(e -> {
            try {
                costumerRepository.delete(customerService.findById(e.getId()));
            } catch (Exception ex) {
                log.error(ex.getMessage());
            }
        });
    }

    @Test
    public void testSave() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        HttpEntity<Costumer> entity = new HttpEntity<>(this.customer, headers);

        CustomerDTO savedCustomer = restTemplate.exchange("/costumers", HttpMethod.POST, entity, CustomerDTO.class).getBody();
        Costumer foundCostumer = customerService.findById(savedCustomer.getId());
        customerList.add(foundCostumer);
        assertNotNull(savedCustomer);
        assertNotNull(savedCustomer.getId());

    }

    //TODO: run test without starting server
    //@Test
    public void testGet() throws URISyntaxException, IOException {
        RestTemplate restTemplate = new RestTemplate();
        String fooResourceUrl
                = "http://localhost:8080/shop/costumers";
        ResponseEntity<String> response
                = restTemplate.getForEntity(fooResourceUrl, String.class);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));

        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(response.getBody());
        JsonNode name = root.path("name");
        assertThat(name.asText(), notNullValue());
    }

    //FIXME: Not working
//    @Test
    @Transactional
    public void testUpdate() {
        customerService.save(customer);
        customerList.add(customer);

        JSONObject request = new JSONObject();
        request.put("phoneNumber1", "044458485");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(request.toString(), headers);

        ResponseEntity<String> updateResponse = restTemplate
                .exchange(String.format("/costumers/%d", customer.getId()), HttpMethod.PUT, entity, String.class);
        Costumer foundCustomer = customerService.findById(customer.getId());
        assertEquals("044458485", foundCustomer.getPhoneNumber1());
    }

    @Test
    public void testDelete() {
        customerService.save(customer);
        customerList.add(customer);
        String entityUrl = String.format("/costumers/%d", customerList.get(0).getId());
        restTemplate.delete(entityUrl);
        Costumer customer = customerService.findById(customerList.get(0).getId());
        assertEquals(RecordStatus.INACTIVE, customer.getRecordStatus());
    }
}
