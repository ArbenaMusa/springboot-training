package com.ucx.training.shop.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ucx.training.shop.dto.LineItemDTO;
import com.ucx.training.shop.entity.LineItem;
import com.ucx.training.shop.entity.Product;
import com.ucx.training.shop.repository.LineItemRepository;
import com.ucx.training.shop.repository.ProductRepository;
import com.ucx.training.shop.service.LineItemService;
import com.ucx.training.shop.service.ProductService;
import com.ucx.training.shop.type.RecordStatus;
import lombok.extern.log4j.Log4j2;
import net.minidev.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
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
    private LineItem lineItem;

    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private LineItemService lineItemService;
    @Autowired
    private LineItemRepository lineItemRepository;

    @Before
    public void setUp(){
        lineItemList = new ArrayList<>();
        Product product = getProduct();
        this.lineItem = new LineItem();
        lineItem.setProduct(product);
        lineItem.setQuantity(2);
    }

    private Product getProduct() {
        Product product = new Product();
        product.setName("Molla");
        product.setUnitPrice(BigDecimal.valueOf(25.5));
        product.setInStock(5);
        return product;
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
    @Ignore
    public void testSave(){
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        HttpEntity<LineItem> entity = new HttpEntity<>(this.lineItem,headers);

        LineItemDTO savedLineItem = restTemplate.exchange("/lineitems", HttpMethod.POST, entity, LineItemDTO.class).getBody();
        LineItem foundLineItem = lineItemService.findById(49);
        lineItemList.add(foundLineItem);
        assertNotNull(savedLineItem);
        assertNotNull(49);
    }

    @Test
    public void testUpdate(){
        lineItemService.save(lineItem);
        lineItemList.add(lineItem);

        JSONObject request = new JSONObject();
        request.put("quantity", "6");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(request.toString(), headers);

        String url = String.format("/lineitems/%d", lineItem.getId());

        ResponseEntity<String> updateResponse = restTemplate
                .exchange(url, HttpMethod.PUT, entity, String.class);

        LineItem foundLineItem = lineItemService.findById(lineItem.getId());
        assertEquals(Integer.valueOf(6),lineItem.getQuantity());
    }

//    @Test
    public void testGet() throws URISyntaxException, IOException {
        RestTemplate restTemplate = new RestTemplate();
        String fooResourceUrl
                = "http://localhost:8080/shop/lineitems";
        ResponseEntity<String> response
                = restTemplate.getForEntity(fooResourceUrl , String.class);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));

        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(response.getBody());
        JsonNode name = root.path("name");
        assertThat(name.asText(), notNullValue());
    }

    @Test
    public void testDelete(){
        lineItemService.save(lineItem);
        lineItemList.add(lineItem);
        String entityUrl = String.format("/lineitems/%d",lineItemList.get(0).getId());
        restTemplate.delete(entityUrl);
        LineItem lineItem = lineItemService.findById(lineItemList.get(0).getId());
        assertEquals(RecordStatus.INACTIVE,lineItem.getRecordStatus());
    }
}