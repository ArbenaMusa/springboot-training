package com.ucx.training.shop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("shop")
public class TestController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping
    public Employee getEmployee(Employee employee){
        return employeeService.save(employee);
    }
}
