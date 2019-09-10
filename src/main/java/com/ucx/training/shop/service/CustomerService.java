package com.ucx.training.shop.service;

import com.ucx.training.shop.entity.Address;
import com.ucx.training.shop.entity.Customer;
import com.ucx.training.shop.entity.Role;
import com.ucx.training.shop.exception.NotFoundException;
import com.ucx.training.shop.repository.CustomerRepository;
import com.ucx.training.shop.type.RecordStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.transaction.Transactional;
import java.util.Base64;
import java.util.List;

@Service
@Transactional
public class CustomerService extends BaseService<Customer, Integer> {

    private CustomerRepository customerRepository;
    private AddressService addressService;
    @Autowired
    private RoleService roleService;


    public CustomerService(CustomerRepository customerRepository, AddressService addressService) {
        this.customerRepository = customerRepository;
        this.addressService = addressService;
    }

    @Override
    public Customer save(Customer customer) {
        if (customer.getAddresses() != null && !customer.getAddresses().isEmpty()) {
            customer.getAddresses().forEach(e -> e.setCustomer(customer));
        }
        //TODO: Default Role for Customer
        final Integer CUSTOMER_ROLE_ID = 2;
        final Role role = roleService.findById(CUSTOMER_ROLE_ID);

        if (customer.getUser() != null) {
            if (role != null) customer.getUser().setRole(role);
            if (customer.getEmail() != null) customer.getUser().setEmail(customer.getEmail());
            String password = customer.getUser().getPassword();
            String encodedPassword = Base64.getEncoder().encodeToString(password.getBytes());
            customer.getUser().setPassword(encodedPassword);
        }
        return super.save(customer);
    }

    public Customer findByEmail(String email) {
        if (email == null) {
            throw new IllegalArgumentException("The given email is null");
        }
        Customer foundCustomer = customerRepository.findByEmail(email);
        if (foundCustomer == null) {
            throw new IllegalArgumentException("There isn't a customer with the given email");
        }
        return foundCustomer;
    }

    public List<Customer> findAllByName(String name) {
        if (name == null) {
            throw new IllegalArgumentException("Name must not be null!");
        }
        return customerRepository.findAllByName(name);
    }

    public Customer updateCostumerWithAddress(Customer newCustomer, Integer costumerId) throws NotFoundException {
        Assert.isTrue(newCustomer != null, "One of the arguments is invalid!");
        Customer foundCustomer = findById(costumerId);
        Assert.isTrue(foundCustomer != null, "Entity not found!");
        //if (foundCostumer == null) throw new NotFoundException("Entity not found");

        if (newCustomer.getAddresses() != null && !newCustomer.getAddresses().isEmpty()) {
            updateAddresses(foundCustomer, newCustomer.getAddresses());
        }

        newCustomer.setAddresses(null);
        return update(newCustomer, costumerId);
    }

    private void updateAddresses(Customer foundCustomer, List<Address> addresses) throws NotFoundException {
        Address newAddress = addresses.get(0);
        if (!foundCustomer.getAddresses().isEmpty()) {
            Address currentAddress = foundCustomer.getAddresses().get(0);
            newAddress.setId(null);
            this.addressService.update(newAddress, currentAddress.getId());
        } else {
            newAddress.setCustomer(foundCustomer);
            addressService.save(newAddress);
        }
    }

    public List<Customer> findAllCustomers() {
        return customerRepository.findAll();
    }

    public List<Customer> findAllActive() {
        return customerRepository.findAllByRecordStatus(RecordStatus.ACTIVE);
    }
}
