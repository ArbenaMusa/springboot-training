package com.ucx.training.shop.dto;

import lombok.Data;

@Data
public class AddressDTO extends DTOEntity {
    private Integer id;
    private String street;
    private Integer zipCode;
    private String city;
    private String country;
}
