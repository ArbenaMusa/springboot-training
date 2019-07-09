package com.ucx.training.shop.service;

import com.ucx.training.shop.type.RecordStatus;
import com.ucx.training.shop.entity.Employee;
import com.ucx.training.shop.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public Employee save(Employee employee){
        if (employee == null) {
            throw new IllegalArgumentException("Invalid argument: "+employee);
        }
        return employeeRepository.save(employee);
    }

    public Employee update(Employee employee, Integer id) {
        if (employee == null || id == null) {
            throw new IllegalArgumentException(String.format("One of the arguments is invalid: %s %d", employee, id));
        }
        Employee foundEmployee = findById(id);
        if (foundEmployee == null) {
            throw new RuntimeException("Employee not found");
        }
        foundEmployee.setName(employee.getName());
        foundEmployee.setAddress(employee.getAddress());
//        return employeeRepository.save(foundEmployee);
        return foundEmployee;
    }

    public List<Employee> findAllByNameAndAddress(String name, String address) {
        if (name == null || address == null) {
            throw new IllegalArgumentException(String.format("Invalid arguments: %s %s", name, address));
        }
        return employeeRepository.findAllByNameAndAddress(name, address);
    }

    public Employee findById(Integer id) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);
        return optionalEmployee.orElse(null);
    }

    public void remove(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("Invalid argument" + id);
        }
        Employee employee = findById(id);
        if (employee == null) {
            throw new RuntimeException("Employee not found");
        }

        employee.setRecordStatus(RecordStatus.INACTIVE);
    }

    public List<Employee> findAllSorted(String direction, String ... properties) {

        if (!Arrays.asList("ASC", "DESC").contains(direction.toUpperCase())) {
            throw new IllegalArgumentException("Value must be either ASC or DESC: " + direction);
        }

        return employeeRepository.findAll(Sort.by(Sort.Direction.valueOf(direction), properties));
    }

    public List<Employee> findAllByNameAndAddressNative(String name, String address) {
        if (name == null || address == null) {
            throw new IllegalArgumentException(String.format("Invalid arguments: %s %s", name, address));
        }
        return employeeRepository.findAllByNameAndAddressNative(name, address);
    }

    public List<Employee> findAllByNameAndAddressJPQL(String name, String address) {
        if (name == null || address == null) {
            throw new IllegalArgumentException(String.format("Invalid arguments: %s %s", name, address));
        }
        return employeeRepository.findAllByNameAndAddressJPQL(name, address);
    }
}
