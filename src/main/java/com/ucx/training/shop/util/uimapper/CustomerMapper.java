package com.ucx.training.shop.util.uimapper;

import com.ucx.training.shop.dto.AddressDTO;
import com.ucx.training.shop.dto.CustomerDTO;
import com.ucx.training.shop.dto.PhoneDTO;
import com.ucx.training.shop.entity.Address;
import com.ucx.training.shop.entity.Costumer;
import com.ucx.training.shop.entity.Phone;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CustomerMapper {

    public CustomerMapper() {

    }

    public static CustomerDTO getCustomer(Costumer costumer) {
        if (costumer == null) {
            throw new IllegalArgumentException("Costumer must not be null!");
        }
        List<Address> addressList = costumer.getAddresses();
        List<AddressDTO> addressDTOList = new ArrayList<>();
        Set<PhoneDTO> phoneDTOS = new HashSet<>();
        Set<Phone> phones = costumer.getPhoneNumbers();

        addressList.forEach(e -> {
            AddressDTO addressDTO = new AddressDTO();
            addressDTO.setStreet(e.getStreet());
            addressDTO.setCity(e.getCity());
            addressDTO.setZipCode(e.getZipCode());
            addressDTO.setCountry(e.getCountry());
            addressDTOList.add(addressDTO);
        });
        phones.forEach(e-> {
                PhoneDTO phoneDTO = new PhoneDTO();
                phoneDTO.setPhoneNumber(e.getPhoneNumber());
                phoneDTOS.add(phoneDTO);
        });

        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setId(costumer.getId());
        customerDTO.setName(costumer.getName());
        customerDTO.setEmail(costumer.getEmail());
        customerDTO.setPhones(phoneDTOS);
        customerDTO.setAddresses(addressDTOList);
        return customerDTO;
    }

    public static List<CustomerDTO> getCustomers(List<Costumer> costumerList) {
        List<CustomerDTO> customerDTOList = new ArrayList<>();
        List<AddressDTO> addressDTOList = new ArrayList<>();
        Set<PhoneDTO> phoneDTOList = new HashSet<>();
        costumerList.forEach(e -> {
            CustomerDTO customerDTO = new CustomerDTO();
            List<Address> addresses = e.getAddresses();
            Set<Phone> phones = e.getPhoneNumbers();
            addresses.forEach(f -> {
                AddressDTO addressDTO = new AddressDTO();
                addressDTO.setCountry(f.getCountry());
                addressDTO.setZipCode(f.getZipCode());
                addressDTO.setCity(f.getCity());
                addressDTO.setStreet(f.getStreet());
                addressDTOList.add(addressDTO);
            });
            phones.forEach(d->{
                PhoneDTO phoneDTO = new PhoneDTO();
                phoneDTO.setPhoneNumber(d.getPhoneNumber());
                phoneDTOList.add(phoneDTO);
            });
            customerDTO.setAddresses(addressDTOList);
            customerDTO.setName(e.getName());
            customerDTO.setPhones(phoneDTOList);
            customerDTO.setEmail(e.getEmail());
            customerDTOList.add(customerDTO);

        });
        return customerDTOList;
    }
}
