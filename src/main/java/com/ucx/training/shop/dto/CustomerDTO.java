package com.ucx.training.shop.dto;

import lombok.Data;

import java.util.List;

@Data
public class CustomerDTO extends DTOEntity{
    private Integer id;
    private String name;
    private String email;
    private String phoneNumber;
    private List<AddressDTO> addresses;

}
