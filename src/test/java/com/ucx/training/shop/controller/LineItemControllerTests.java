package com.ucx.training.shop.controller;

import com.ucx.training.shop.dto.LineItemDTO;
import com.ucx.training.shop.entity.LineItem;
import com.ucx.training.shop.entity.Product;
import com.ucx.training.shop.repository.LineItemRepository;
import com.ucx.training.shop.service.LineItemService;
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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertNotNull;

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
        product.setName("Pjeshka");
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
}
