package com.ucx.training.shop.util;

import com.ucx.training.shop.dto.AddressDTO;
import com.ucx.training.shop.dto.CustomerDTO;
import com.ucx.training.shop.entity.Address;
import com.ucx.training.shop.entity.Costumer;
import java.util.ArrayList;
import java.util.List;

public class CustomerUtil {

    public CustomerUtil() {

    }

    public static CustomerDTO getCustomer(Costumer costumer) {
        CustomerDTO customerDTO = new CustomerDTO();
        AddressDTO addressDTO = new AddressDTO();
        List<Address> addressList = costumer.getAddresses();
        List<AddressDTO> addressDTOList = new ArrayList<>();

        addressList.forEach(e -> {
            addressDTO.setStreet(e.getStreet());
            addressDTO.setCity(e.getCity());
            addressDTO.setZipCode(e.getZipCode());
            addressDTO.setCountry(e.getCountry());
            addressDTOList.add(addressDTO);
        });

        customerDTO.setName(costumer.getName());
        customerDTO.setEmail(costumer.getEmail());
        customerDTO.setPhoneNumber1(costumer.getPhoneNumber1());
        customerDTO.setPhoneNumber2(costumer.getPhoneNumber2());
        customerDTO.setAddresses(addressDTOList);
        return customerDTO;
    }

    public static List<CustomerDTO> getCustomers(List<Costumer> costumerList) {
        List<CustomerDTO> customerDTOList = new ArrayList<>();
        List<AddressDTO> addressDTOList = new ArrayList<>();
        costumerList.forEach(e -> {
            CustomerDTO customerDTO = new CustomerDTO();
            List<Address> addresses = new ArrayList<>();
            addresses = e.getAddresses();
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
            customerDTO.setPhoneNumber2(e.getPhoneNumber2());
            customerDTO.setPhoneNumber1(e.getPhoneNumber1());
            customerDTO.setEmail(e.getEmail());
            customerDTOList.add(customerDTO);

        });
        return customerDTOList;
    }
}
