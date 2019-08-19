package com.ucx.training.shop.service.integ;

import com.ucx.training.shop.entity.Address;
import com.ucx.training.shop.entity.Customer;
import com.ucx.training.shop.repository.CustomerRepository;
import com.ucx.training.shop.service.CustomerService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
public class CustomerServiceTests {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CustomerRepository customerRepository;

    @Before
    public void setup(){
    }

    @After
    public void cleanup(){
    }

    /**
     * This method tests customer creation
     *
     * Customer cannot be created without at least 1 address!
     */
    @Test
    public void testCreate() {
        Customer customer = new Customer();
        customer.setName("TestFilani");
        customer.setAddresses(Arrays.asList(new Address("Rruga", 1000, "Prishtina", "Kosova", null)));
        Customer createdCustomer = customerService.save(customer);
        assertNotNull(createdCustomer);
        assertNotNull(createdCustomer.getId());
        Customer foundCustomer = customerService.findById(createdCustomer.getId());
        assertEquals(foundCustomer.getId(), createdCustomer.getId());
        cleanCustomers(createdCustomer);
    }

    private void cleanCustomers(Customer... customers){
        Arrays.asList(customers).forEach(e-> customerRepository.delete(e));
    }
}
