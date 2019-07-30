package com.ucx.training.shop.dto;

import lombok.Data;

import java.util.List;

@Data
public class CustomerDTO implements DTOEntity{
    private Integer id;
    private String name;
    private String email;
    private List<PhoneDTO> phoneDTOS;
    private List<AddressDTO> addresses;

}
