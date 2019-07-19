package com.ucx.training.shop.service.integ;

import com.ucx.training.shop.entity.Product;
import com.ucx.training.shop.exception.NotFoundException;
import com.ucx.training.shop.repository.ProductRepository;
import com.ucx.training.shop.service.ProductService;
import com.ucx.training.shop.type.RecordStatus;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductServiceTests {

    @Autowired
    private ProductService productService;
    @Autowired
    private ProductRepository productRepository;

    private List<Product> productList;

    @Before
    public void setup() {
        productList = new ArrayList<>();
    }

    @After
    public void cleanup() {
        productList.forEach(e -> productRepository.delete(e));
    }

    @Test
    public void testCreate() {
        Product product = new Product();
        product.setName("TestProdukt");
        product.setInStock(10);
        product.setUnitPrice(BigDecimal.valueOf(12.5));
        Product createdProduct = productService.save(product);
        assertNotNull(createdProduct);
        assertNotNull(createdProduct.getId());
        Product foundProduct = productService.findById(createdProduct.getId());
        assertEquals(foundProduct.getId(), createdProduct.getId());
        productList.add(createdProduct);
    }

    @Test
    public void testDelete() throws NotFoundException {
        Product product = new Product();
        product.setName("TestProdukt");
        product.setInStock(10);
        product.setUnitPrice(BigDecimal.valueOf(12.5));
        Product createdProduct = productService.save(product);
        assertNotNull(createdProduct);
        assertNotNull(createdProduct.getId());
        productService.remove(createdProduct.getId());
        Product foundProduct = productService.findById(createdProduct.getId());
        assertEquals(RecordStatus.INACTIVE, createdProduct.getRecordStatus());
        productList.add(createdProduct);
    }

    @Test
    public void testUpdate() throws NotFoundException {
        Product product = new Product();
        product.setName("TestProdukt");
        product.setInStock(10);
        product.setUnitPrice(BigDecimal.valueOf(12.5));
        Product createdProduct = productService.save(product);
        assertNotNull(createdProduct);
        assertNotNull(createdProduct.getId());

        Product productUpdated = new Product();
        product.setName("ProduktTest");
        product.setInStock(20);
        product.setUnitPrice(BigDecimal.valueOf(13.4));

        Product updatedProduct = productService.update(productUpdated, createdProduct.getId());
        assertEquals("ProduktTest", updatedProduct.getName());
        productList.add(createdProduct);
    }


}
