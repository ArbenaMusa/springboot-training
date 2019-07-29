package com.ucx.training.shop.util.uimapper;

import com.ucx.training.shop.dto.AddressDTO;
import com.ucx.training.shop.dto.CustomerDTO;
import com.ucx.training.shop.dto.PhoneDTO;
import com.ucx.training.shop.entity.Address;
import com.ucx.training.shop.entity.Customer;
import java.util.ArrayList;
import java.util.List;

public class CustomerMapper {

    private CustomerMapper() {}

    public static CustomerDTO getCustomer(Customer customer) {
        if (customer == null) {
            throw new IllegalArgumentException("Costumer must not be null!");
        }
        List<Address> addressList = customer.getAddresses();
        List<AddressDTO> addressDTOList = new ArrayList<>();
        List<PhoneDTO> phoneDTOList = new ArrayList<>();

        addressList.forEach(e -> {
            AddressDTO addressDTO = new AddressDTO();
            addressDTO.setStreet(e.getStreet());
            addressDTO.setCity(e.getCity());
            addressDTO.setZipCode(e.getZipCode());
            addressDTO.setCountry(e.getCountry());
            addressDTOList.add(addressDTO);
        });
        phoneDTOList.forEach(e -> {
            PhoneDTO phoneDTO = new PhoneDTO();
            phoneDTO.setPhoneNumber(e.getPhoneNumber());
            phoneDTOList.add(phoneDTO);
        });

        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setId(customer.getId());
        customerDTO.setName(customer.getName());
        customerDTO.setEmail(customer.getEmail());
        customerDTO.setPhoneDTOS(phoneDTOList);
        customerDTO.setAddresses(addressDTOList);
        return customerDTO;
    }

    public static List<CustomerDTO> getCustomers(List<Customer> customerList) {
        List<CustomerDTO> customerDTOList = new ArrayList<>();
        List<AddressDTO> addressDTOList = new ArrayList<>();
        List<PhoneDTO> phoneDTOList = new ArrayList<>();

        customerList.forEach(e -> {
            CustomerDTO customerDTO = new CustomerDTO();
            List<Address> addresses = e.getAddresses();
            addresses.forEach(f -> {
                AddressDTO addressDTO = new AddressDTO();
                addressDTO.setCountry(f.getCountry());
                addressDTO.setZipCode(f.getZipCode());
                addressDTO.setCity(f.getCity());
                addressDTO.setStreet(f.getStreet());
                addressDTOList.add(addressDTO);
            });
            customerDTO.setAddresses(addressDTOList);
            customerDTO.setName(e.getName());
            customerDTO.setPhoneDTOS(phoneDTOList);
            customerDTO.setEmail(e.getEmail());
            customerDTOList.add(customerDTO);

        });
        phoneDTOList.forEach(e -> {
            PhoneDTO phoneDTO = new PhoneDTO();
            phoneDTO.setPhoneNumber(e.getPhoneNumber());
            phoneDTOList.add(phoneDTO);
        });
        return customerDTOList;
    }
}
