package com.ucx.training.shop.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ucx.training.shop.dto.LineItemDTO;
import com.ucx.training.shop.entity.LineItem;
import com.ucx.training.shop.entity.Product;
import com.ucx.training.shop.repository.LineItemRepository;
import com.ucx.training.shop.service.LineItemService;
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

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.*;

@Log4j2
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LineItemControllerTests {

    private List<LineItem> lineItemList;

    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private LineItemService lineItemService;
    @Autowired
    private LineItemRepository lineItemRepository;

    @Before
    public void setUp(){
        lineItemList = new ArrayList<>();
    }

    @After
    public void cleanUp(){
        lineItemList.forEach(e -> {
            try {
                lineItemRepository.delete(lineItemService.findById(e.getId()));
            } catch (Exception ex) {
                log.error(ex.getMessage());
            }
        });
    }

    @Test
    public void testSave(){
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        Product product = new Product();
        product.setName("Molla");
        product.setUnitPrice(BigDecimal.valueOf(25.5));
        product.setInStock(5);

        LineItem lineItem = new LineItem();
        lineItem.setProduct(product);
        lineItem.setQuantity(2);

        HttpEntity<LineItem> entity = new HttpEntity<>(lineItem,headers);

        LineItemDTO savedLineItem = restTemplate.exchange("/lineitems", HttpMethod.POST, entity, LineItemDTO.class).getBody();

        assertNotNull(savedLineItem);
        //TODO:delete from database after you insert lineItem and product also
//        lineItemList.add(savedLineItem);
    }

    @Test
    public void testUpdate(){
        JSONObject request = new JSONObject();
        request.put("quantity", "1");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(request.toString(), headers);

        ResponseEntity<String> updateResponse = restTemplate
                .exchange("/lineitems/13", HttpMethod.PUT, entity, String.class);

        LineItem lineItem = lineItemService.findById(13);
        assertEquals(Integer.valueOf(1),lineItem.getQuantity());
    }

//    @Test
//    public void testGet() throws URISyntaxException, IOException {
//        RestTemplate restTemplate = new RestTemplate();
//        String fooResourceUrl
//                = "http://localhost:8080/shop/lineitems";
//        ResponseEntity<String> response
//                = restTemplate.getForEntity(fooResourceUrl , String.class);
//        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
//
//        ObjectMapper mapper = new ObjectMapper();
//        JsonNode root = mapper.readTree(response.getBody());
//        JsonNode name = root.path("name");
//        assertThat(name.asText(), notNullValue());
//    }

//    @Test
//    public void testDelete(){
//        String entityUrl = "/lineitems/7";
//        restTemplate.delete(entityUrl);
//        LineItem lineItem = lineItemService.findById(7);
//        assertEquals(RecordStatus.INACTIVE,lineItem.getRecordStatus());
//    }
}
