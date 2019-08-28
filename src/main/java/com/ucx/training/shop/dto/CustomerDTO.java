package com.ucx.training.shop.dto;

import lombok.Data;

import java.util.List;

@Data
public class CustomerDTO extends DTOEntity{
    private String name;
    private String email;
    private List<PhoneDTO> phoneNumbers;
    private List<AddressDTO> addresses;

}
