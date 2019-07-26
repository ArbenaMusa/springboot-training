package com.ucx.training.shop.controller;

import com.ucx.training.shop.dto.CustomerDTO;
import com.ucx.training.shop.entity.Address;
import com.ucx.training.shop.entity.Costumer;
import com.ucx.training.shop.repository.CostumerRepository;
import com.ucx.training.shop.service.CostumerService;
import com.ucx.training.shop.util.JwtUtil;
import lombok.extern.log4j.Log4j2;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

import static org.junit.Assert.assertNotNull;

@Log4j2
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CustomerControllerTests {

    private List<Integer> customerList;
    private Map<String, String> tokenMap;
    @Value("${jwt.filter.enabled}")
    private String applyJwtFilter;

    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private CostumerService customerService;
    @Autowired
    private CostumerRepository costumerRepository;

    @Before
    public void setUp() {
        customerList = new ArrayList<>();
        if (JwtUtil.applyJwtFilter(applyJwtFilter)){
            String emailAndPassword = "user@shop.com;user";
            String encodedEmailAndPassword = Base64.getEncoder().encodeToString(emailAndPassword.getBytes());
            Map<String, String> credentialsMap = new HashMap<>();
            credentialsMap.put("creds", encodedEmailAndPassword);
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
            HttpEntity<Map<String, String>> entity = new HttpEntity<>(credentialsMap, headers);
            tokenMap = restTemplate.exchange("/tokens", HttpMethod.POST, entity, HashMap.class).getBody();
        }
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
        if (JwtUtil.applyJwtFilter(applyJwtFilter)) {
            headers.add("Authorization", "Bearer " + tokenMap.get("accessToken"));
        }
        Costumer customer = new Costumer();
        customer.setName("testName");
        customer.setAddresses(Arrays.asList(new Address("Rruga", 1000, "Prishtina", "Kosova", null)));

        HttpEntity<Costumer> entity = new HttpEntity<>(customer, headers);

        CustomerDTO savedCustomer = restTemplate.exchange("/v1/costumers", HttpMethod.POST, entity, CustomerDTO.class).getBody();

        assertNotNull(savedCustomer);
        assertNotNull(savedCustomer.getId());
        customerList.add(savedCustomer.getId());
    }
}
