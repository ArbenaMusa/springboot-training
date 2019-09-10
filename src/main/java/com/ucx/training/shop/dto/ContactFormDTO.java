package com.ucx.training.shop.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ContactFormDTO {

    @NotNull
    private String name;
    @NotNull
    private String email;
    @NotNull
    private String message;
}
