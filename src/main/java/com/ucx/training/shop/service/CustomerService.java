package com.ucx.training.shop.service;

import com.ucx.training.shop.entity.*;
import com.ucx.training.shop.exception.NotFoundException;
import com.ucx.training.shop.repository.CustomerRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.persistence.Tuple;
import javax.transaction.Transactional;
import java.util.Base64;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class CustomerService extends BaseService<Customer, Integer> {

    private CustomerRepository customerRepository;
    private AddressService addressService;
    private PhoneService phoneService;
    @Autowired
    private RoleService roleService;


    public CustomerService(CustomerRepository customerRepository, AddressService addressService, PhoneService phoneService) {
        this.customerRepository = customerRepository;
        this.addressService = addressService;
        this.phoneService = phoneService;
    }

    @Override
    public Customer save(Customer customer) {
        if (customer.getAddresses() != null && !customer.getAddresses().isEmpty()) {
            customer.getAddresses().forEach(e -> e.setCustomer(customer));
        }
        if (customer.getPhoneNumbers() != null && !customer.getPhoneNumbers().isEmpty()) {
            customer.getPhoneNumbers().forEach(e -> e.setCustomer(customer));
        }
        //TODO: Default Role for Customer
        final Integer CUSTOMER_ROLE_ID = 1;
        final Role role = roleService.findById(CUSTOMER_ROLE_ID);

        if (customer.getUser() != null) {
            if(role != null) customer.getUser().setRole(role);
            if(customer.getEmail() != null) customer.getUser().setEmail(customer.getEmail());
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

    public void updateWithAddresses(Customer t, Integer u) throws NotFoundException {
        if (t == null) {
            throw new IllegalArgumentException(String.format("One of the arguments is invalid: %s", t));
        }
        Customer foundT = findById(u);
        if (foundT == null) {
            throw new NotFoundException("Entity not found");
        }
        List<Address> previousAddressList = foundT.getAddresses();
        List<Address> receivedAddressList = t.getAddresses();
        if (receivedAddressList != null) {
            previousAddressList.stream().forEach((e) -> {
                Iterator<Address> iterator = receivedAddressList.iterator();
                while (iterator.hasNext()) {
                    Address i = iterator.next();
                    try {
                        if (e.getId().equals(i.getId())) {
                            BeanUtils.copyProperties(i, e, BaseService.<Address>getNullPropertyNames(i));
                            iterator.remove();
                        }
                    } catch (Exception m) {
                        //exception presumably nullPointerException was suppressed
                    }
                }
            });
        }
        receivedAddressList.forEach((e) -> {
            e.setCustomer(foundT);
            addressService.save(e);
        });
        t.setAddresses(null);
        BeanUtils.copyProperties(t, foundT, BaseService.<Customer>getNullPropertyNames(t));
    }

    public Customer updateCostumerWithAddress(Customer newCustomer, Integer costumerId) throws NotFoundException {
        Assert.isTrue(newCustomer != null, "One of the arguments is invalid!");
        Customer foundCustomer = findById(costumerId);
        Assert.isTrue(foundCustomer != null, "Entity not found!");
        //if (foundCostumer == null) throw new NotFoundException("Entity not found");

        if (!newCustomer.getAddresses().isEmpty()) {
            updateAddresses(foundCustomer, newCustomer.getAddresses());
        }
        if (!newCustomer.getPhoneNumbers().isEmpty()) {
            updatePhones(foundCustomer, newCustomer.getPhoneNumbers());
        }

        newCustomer.setAddresses(null);
        return update(newCustomer, costumerId);
    }

    private void updateAddresses(Customer foundCustomer, List<Address> addresses) throws NotFoundException {
        for (Address address : addresses) {
            if (address.getId() == null) {
                address.setCustomer(foundCustomer);
                addressService.save(address);
            } else {
                Address foundAddress = addressService.findById(address.getId());
                if (foundAddress == null) {
                    throw new NotFoundException("Address with the given id does not exist!");
                }
                if (!foundAddress.getCustomer().equals(foundCustomer)) {
                    throw new RuntimeException("This address does not belong to this customer");
                }
                addressService.update(address, foundAddress.getId());
            }
        }
    }

    private void updatePhones(Customer foundCustomer, Set<Phone> phones) throws NotFoundException {
        for (Phone phone : phones) {
            if (phone.getId() == null) {
                phone.setCustomer(foundCustomer);
                phoneService.save(phone);
            } else {
                Phone foundPhone = phoneService.findById(phone.getId());
                if (foundPhone == null) {
                    throw new NotFoundException("Address with the given id does not exist!");
                }
                if (!foundPhone.getCustomer().equals(foundCustomer)) {
                    throw new RuntimeException("This address does not belong to this customer");
                }
                phoneService.update(phone, foundPhone.getId());
            }
        }
    }

//    public Tuple readByCostumerId(Integer id) {
//        return customerRepository.readCostumerById(id);
//    }

    public List<Customer> findAllCustomers()
    {
        return customerRepository.findAll();
    }

    public List<Customer> findAllActive() {
        return customerRepository.findAllActive();
    }
}
