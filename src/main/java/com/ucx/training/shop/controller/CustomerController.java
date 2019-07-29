package com.ucx.training.shop.controller;

import com.ucx.training.shop.dto.CustomerDTO;
import com.ucx.training.shop.entity.Customer;
import com.ucx.training.shop.exception.NotFoundException;
import com.ucx.training.shop.exception.ResponseException;
import com.ucx.training.shop.service.CustomerService;
import com.ucx.training.shop.util.EntityUtil;
import com.ucx.training.shop.util.uimapper.CustomerMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.persistence.Tuple;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Log4j2
@RestController
@RequestMapping("v1/costumers")
public class CustomerController {

    private CustomerService customerService;

    private CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    public Customer create(@RequestBody Customer costumer) throws ResponseException {
        try {
            Customer customer = customerService.save(costumer);
            return customer;//CustomerMapper.getCustomer(customer);
        } catch (IllegalArgumentException e) {
            throw new ResponseException(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            throw new ResponseException(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("{id}")
    public Map<String, Integer> update(@RequestBody Customer customer, @PathVariable Integer id) throws ResponseException {
        Map<String, Integer> responseMap = new HashMap<>();
        try {
            customerService.updateWithAddresses(customer, id);
            responseMap.put("id", id);
        } catch (NotFoundException | IllegalArgumentException e) {
            throw new ResponseException(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            throw new ResponseException(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return responseMap;
    }

    @GetMapping("{costumerId}")
    public CustomerDTO getById(@PathVariable Integer costumerId) {
        Customer foundCustomer = customerService.findById(costumerId);
        return CustomerMapper.getCustomer(foundCustomer);
    }

    @DeleteMapping("{id}")
    public void remove(@PathVariable Integer id) throws ResponseException {
        try {
            customerService.remove(id);
        } catch (NotFoundException | IllegalArgumentException e) {
            throw new ResponseException(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            throw new ResponseException(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public List<CustomerDTO> findAllSorted(@RequestParam(required = false, defaultValue = "ASC") String direction, @RequestParam(defaultValue = "id") String... properties) throws ResponseException {
        try {
            List<Customer> customers = customerService.findAllSorted(direction, properties);
            List<CustomerDTO> customerDTOList = CustomerMapper.getCustomers(customers);
            return customerDTOList;
        } catch (Exception e) {
            throw new ResponseException(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/paged")
    public List<CustomerDTO> findAllPaged(@RequestParam int pageNumber, @RequestParam int pageSize) throws ResponseException {
        try {
            Page<Customer> costumerPage = customerService.findAllPaged(pageNumber, pageSize);
            List<Customer> customers = costumerPage.getContent();
            List<CustomerDTO> customerDTOList = CustomerMapper.getCustomers(customers);
            return customerDTOList;
        } catch (Exception e) {
            throw new ResponseException(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/read/{id}")
    public Map<String, Object> readById(@PathVariable Integer id) {
        Tuple tuple = customerService.readByCostumerId(id);
        return EntityUtil.toList(tuple);
    }
}
