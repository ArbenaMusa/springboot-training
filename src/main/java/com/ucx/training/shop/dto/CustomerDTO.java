package com.ucx.training.shop.dto;

import lombok.Data;
import java.util.List;
import java.util.Set;

@Data
public class CustomerDTO {
    private Integer id;
    private String name;
    private String email;
    private Set<PhoneDTO> phones;
    private List<AddressDTO> addresses;

}
