package com.ucx.training.shop.util.uimapper;

import com.ucx.training.shop.dto.AddressDTO;
import com.ucx.training.shop.dto.CustomerDTO;
import com.ucx.training.shop.dto.PhoneDTO;
import com.ucx.training.shop.entity.Address;
import com.ucx.training.shop.entity.Customer;
import com.ucx.training.shop.entity.Phone;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class CustomerMapper {

    private CustomerMapper() {}

    public static CustomerDTO getCustomer(Customer customer) {
        if (customer == null) {
            throw new IllegalArgumentException("Costumer must not be null!");
        }

        List<Address> addressList = customer.getAddresses();
        List<AddressDTO> addressDTOList = new ArrayList<>();
        Set<Phone> phoneList = customer.getPhoneNumbers();
        List<PhoneDTO> phoneDTOList = new ArrayList<>();

        if (!addressList.isEmpty()) {
            convertAddressToDTOList(addressList, addressDTOList);
        }
        if (!phoneList.isEmpty()) {
            convertPhoneToDTOList(phoneList, phoneDTOList);
        }

        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setId(customer.getId());
        customerDTO.setName(customer.getName());
        customerDTO.setEmail(customer.getEmail());
        customerDTO.setPhoneDTOS(phoneDTOList);
        customerDTO.setAddresses(addressDTOList);
        return customerDTO;
    }

    private static void convertPhoneToDTOList(Set<Phone> phoneList, List<PhoneDTO> phoneDTOList) {
        phoneList.forEach(e -> {
            PhoneDTO phoneDTO = new PhoneDTO();
            phoneDTO.setPhoneNumber(e.getPhoneNumber());
            phoneDTOList.add(phoneDTO);
        });
    }

    private static void convertAddressToDTOList(List<Address> addressList, List<AddressDTO> addressDTOList) {
        addressList.forEach(e -> {
            AddressDTO addressDTO = new AddressDTO();
            addressDTO.setStreet(e.getStreet());
            addressDTO.setCity(e.getCity());
            addressDTO.setZipCode(e.getZipCode());
            addressDTO.setCountry(e.getCountry());
            addressDTOList.add(addressDTO);
        });
    }

    public static List<CustomerDTO> getCustomers(List<Customer> customerList) {
        return customerList.stream().map(CustomerMapper::getCustomer).collect(Collectors.toList());
    }
}
