package com.ucx.training.shop.controller;

import com.ucx.training.shop.dto.CustomerDTO;
import com.ucx.training.shop.dto.DTOEntity;
import com.ucx.training.shop.entity.Customer;
import com.ucx.training.shop.exception.NotFoundException;
import com.ucx.training.shop.exception.ResponseException;
import com.ucx.training.shop.service.CustomerService;
import com.ucx.training.shop.util.EntityUtil;
import com.ucx.training.shop.util.uimapper.DTOMapper;
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
@RequestMapping("v1/customers")
public class CustomerController {

    private CustomerService customerService;

    private CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    public DTOEntity create(@RequestBody Customer costumer) throws ResponseException {
        try {
            Customer customer = customerService.save(costumer);
            return new DTOMapper().convertToDto(customer,new CustomerDTO());
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
            customerService.updateCostumerWithAddress(customer, id);
            responseMap.put("id", id);
        } catch (NotFoundException | IllegalArgumentException e) {
            throw new ResponseException(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            throw new ResponseException(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return responseMap;
    }

    @GetMapping("{costumerId}")
    public DTOEntity getById(@PathVariable Integer costumerId) {
        Customer foundCustomer = customerService.findById(costumerId);
        return new DTOMapper().convertToDto(foundCustomer,new CustomerDTO());
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
    public List<DTOEntity> findAllSorted(@RequestParam(required = false, defaultValue = "ASC") String direction, @RequestParam(defaultValue = "id") String... properties) throws ResponseException {
        try {
            List<Customer> customers = customerService.findAllSorted(direction, properties);
            return DTOMapper.converToDTOList(customers,new CustomerDTO());
        } catch (Exception e) {
            throw new ResponseException(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/paged")
    public List<DTOEntity> findAllPaged(@RequestParam int pageNumber, @RequestParam int pageSize) throws ResponseException {
        try {
            Page<Customer> costumerPage = customerService.findAllPaged(pageNumber, pageSize);
            List<Customer> customers = costumerPage.getContent();
            return DTOMapper.converToDTOList(customers,new CustomerDTO());
        } catch (Exception e) {
            throw new ResponseException(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/read/{id}")
    public Map<String, Object> readById(@PathVariable Integer id) {
        Tuple tuple = customerService.readByCostumerId(id);
        return EntityUtil.toMap(tuple);
    }
}
