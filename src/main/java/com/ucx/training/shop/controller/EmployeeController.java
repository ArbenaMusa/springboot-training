package com.ucx.training.shop.controller;

import com.ucx.training.shop.service.EmployeeService;
import com.ucx.training.shop.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("shop/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping
    public Employee saveEmployee(@RequestBody Employee employee){
        return employeeService.save(employee);
    }

    @GetMapping("{id}")
    public Employee getEmployeeById(@PathVariable Integer id) {
        return employeeService.findById(id);
    }

    @PutMapping("{id}")
    public Employee update(@RequestBody Employee employee, @PathVariable Integer id) {
        return employeeService.update(employee, id);
    }

    @DeleteMapping("{id}")
    public void remove(@PathVariable Integer id) {
        employeeService.remove(id);
    }

    @GetMapping
    public List<Employee> findAllSorted(@RequestParam(required = false, defaultValue = "ASC") String direction, @RequestParam(defaultValue = "id") String ... properties) {
        return employeeService.findAllSorted(direction, properties);
    }

    @GetMapping("/bynameandaddress")
    public List<Employee> findAllByNameAndAddress(@RequestParam String name, @RequestParam String address) {
        return employeeService.findAllByNameAndAddress(name, address);
    }

    @GetMapping("/native")
    public List<Employee> findAllByNameAndAddressNative(@RequestParam String name, @RequestParam String address) {
        return employeeService.findAllByNameAndAddressNative(name, address);
    }

    @GetMapping("/jpqlquery")
    public List<Employee> findAllByNameAndAddressJPQL(@RequestParam String name, @RequestParam String address) {
        return employeeService.findAllByNameAndAddressJPQL(name, address);
    }
}
