package com.ucx.training.shop.util;

import com.ucx.training.shop.dto.AddressDTO;
import com.ucx.training.shop.entity.Address;

public class AddressUtil {
    public static AddressDTO getAddress(Address address){
        if (address == null) {
            throw new IllegalArgumentException("Address must not be null");
        }
        AddressDTO addressDTO=new AddressDTO();
        addressDTO.setCity(address.getCity());
        addressDTO.setCountry(address.getCountry());
        addressDTO.setStreet(address.getStreet());
        addressDTO.setZipCode(address.getZipCode());
        return addressDTO;
    }
}
