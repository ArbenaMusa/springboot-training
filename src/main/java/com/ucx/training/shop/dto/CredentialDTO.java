package com.ucx.training.shop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CredentialDTO extends DTOEntity{
    private String email;
    private String password;
}
