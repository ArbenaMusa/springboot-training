package com.ucx.training.shop.controller;

import com.ucx.training.shop.dto.CustomerDTO;
import com.ucx.training.shop.entity.Costumer;
import com.ucx.training.shop.exception.NotFoundException;
import com.ucx.training.shop.service.CostumerService;
import com.ucx.training.shop.util.CustomerUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log4j2
@RestController
@RequestMapping("costumers")
public class CostumerController {

    private CostumerService costumerService;

    private CostumerController(CostumerService costumerService) {
        this.costumerService = costumerService;
    }


    @PostMapping("customer")
    public CustomerDTO create(@RequestBody Costumer costumer) {
        Costumer customer = costumerService.save(costumer);
        return CustomerUtil.getCustomer(customer);
    }

    @PutMapping("customer/{id}")
    public CustomerDTO update(@RequestBody Costumer costumer, @PathVariable Integer id) {
        CustomerDTO customerDTO = null;
        try {
            Costumer updatedCustomer = costumerService.update(costumer, id);
            customerDTO = CustomerUtil.getCustomer(updatedCustomer);
        } catch (NotFoundException e) {
            log.info(e.getMessage());
        }
        return customerDTO;
    }

    @DeleteMapping("customer/{id}")
    public void remove(@PathVariable Integer id) {
        try {
            costumerService.remove(id);
        } catch (NotFoundException e) {
            log.info(e.getMessage());
        }
    }

    @GetMapping("customers")
    public List<CustomerDTO> findAllSorted(@RequestParam(required = false, defaultValue = "ASC") String direction, @RequestParam(defaultValue = "id") String... properties) {
        List<Costumer> costumers = costumerService.findAllSorted(direction, properties);
        List<CustomerDTO> customerDTOList = CustomerUtil.getCustomers(costumers);
        return customerDTOList;
    }

    @GetMapping("/pages")
    public List<CustomerDTO> findAllPaged(@RequestParam int pageNumber, @RequestParam int pageSize) {
        Page<Costumer> costumerPage = costumerService.findAllPaged(pageNumber, pageSize);
        List<Costumer> costumers = costumerPage.getContent();
        List<CustomerDTO> customerDTOList = CustomerUtil.getCustomers(costumers);
        return customerDTOList;
    }

}
