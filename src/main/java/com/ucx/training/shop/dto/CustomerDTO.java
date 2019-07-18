package com.ucx.training.shop.dto;

import lombok.Data;
import java.util.List;

@Data
public class CustomerDTO {
    private Integer id;
    private String name;
    private String email;
    private String phoneNumber1;
    private String phoneNumber2;
    private List<AddressDTO> addresses;

}
