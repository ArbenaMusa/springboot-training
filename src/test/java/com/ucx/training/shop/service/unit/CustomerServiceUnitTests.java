package com.ucx.training.shop.service.unit;

import com.ucx.training.shop.entity.Address;
import com.ucx.training.shop.entity.Customer;
import com.ucx.training.shop.service.CustomerService;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.Arrays;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
public class CustomerServiceUnitTests {

    @MockBean
    private CustomerService customerService;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @Ignore
    public void testCreate() {
        Customer customer = new Customer();
        customer.setName("TestFilani");
        customer.setAddresses(Arrays.asList(new Address("Rruga", 1000, "Prishtina", "Kosova", null)));

        Customer createdCustomer = new Customer();
        createdCustomer.setId(1);
        createdCustomer.setName("TestFilani");
        createdCustomer.setAddresses(Arrays.asList(new Address("Rruga", 1000, "Prishtina", "Kosova", null)));

        when(customerService.save(customer)).thenReturn(createdCustomer);
        Customer cust = customerService.save(customer);
        Mockito.verify(customerService, times(1)).save(customer);
        assertEquals(Integer.valueOf(1), cust.getId());
    }
}
