package com.ucx.training.shop.util.uimapper;

import com.ucx.training.shop.dto.AddressDTO;
import com.ucx.training.shop.entity.Address;

public class AddressMapper {

    public static Address mapToAddress(AddressDTO addressDTO) {
        Address returnAddress = new Address();
        if (addressDTO == null) {
            throw new IllegalArgumentException("The given address dto is null");
        }
        Integer dtoId = addressDTO.getId();
        String dtoCity = addressDTO.getCity();
        String dtoCountry = addressDTO.getCountry();
        Integer dtoZipCode = addressDTO.getZipCode();
        String street = addressDTO.getStreet();
        if (dtoId != null) {
            returnAddress.setId(dtoId);
            return returnAddress;
        }
        if (dtoCity != null) {
            returnAddress.setCity(dtoCity);
        }
        if (dtoCountry != null) {
            returnAddress.setCountry(dtoCountry);
        }
        if (dtoZipCode != null) {
            returnAddress.setZipCode(dtoZipCode);
        }
        if (street != null) {
            returnAddress.setStreet(street);
        }
        return returnAddress;
    }
}
