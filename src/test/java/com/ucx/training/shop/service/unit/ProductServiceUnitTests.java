package com.ucx.training.shop.service.unit;

import com.ucx.training.shop.entity.Product;
import com.ucx.training.shop.exception.NotFoundException;
import com.ucx.training.shop.service.ProductService;
import com.ucx.training.shop.type.RecordStatus;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductServiceUnitTests {

    @Mock
    private ProductService productService;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreate() {
        Product product = new Product();
        product.setName("test-produkt");
        product.setUnitPrice(BigDecimal.valueOf(10.5));
        product.setInStock(10);

        Product createdProduct = new Product();
        createdProduct.setId(1);
        createdProduct.setName("test-produkt");
        product.setUnitPrice(BigDecimal.valueOf(10.5));
        product.setInStock(10);

        when(productService.save(product)).thenReturn(createdProduct);
        assertEquals(Integer.valueOf(1), createdProduct.getId());
    }

    @Test
    @Ignore
    public void testDelete() throws NotFoundException {
        Product product = new Product();
        product.setName("test-produkt");
        product.setUnitPrice(BigDecimal.valueOf(10.5));
        product.setInStock(10);

        doNothing().when(productService).remove(product.getId());
        productService.remove(1);

        verify(productService, times(1)).remove(1);
        assertEquals(RecordStatus.INACTIVE, product.getRecordStatus());
    }

    @Test
    public void testUpdate() throws NotFoundException {
        Product product = new Product();
        product.setName("test-produkt");
        product.setUnitPrice(BigDecimal.valueOf(10.5));
        product.setInStock(10);

        Product createdProduct = new Product();
        createdProduct.setId(1);
        createdProduct.setName("produkt-test");
        createdProduct.setUnitPrice(BigDecimal.valueOf(10.5));
        createdProduct.setInStock(10);

        when(productService.update(createdProduct, product.getId())).thenReturn(product);
        assertEquals("produkt-test", createdProduct.getName());
    }

    @Test
    public void whenFindAll_thenReturnCostumerList() {
        Product product = new Product();
        product.setName("test-produkt");
        product.setUnitPrice(BigDecimal.valueOf(10.5));
        product.setInStock(10);

        List<Product> productList = Arrays.asList(product);

        doReturn(productList).when(productService).findAll();

        List<Product> foundProducts = productService.findAll();

        assertEquals(foundProducts, productList);
    }

    @Test
    public void whenGivenId_findById_thenReturnProduct() {
        Product product = new Product();
        product.setName("test-produkt");
        product.setUnitPrice(BigDecimal.valueOf(10.5));
        product.setInStock(10);

        when(productService.findById(1)).thenReturn(product);
        Product foundProduct = productService.findById(1);

        assertEquals(foundProduct, product);
    }
}
