package com.ucx.training.shop.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Costumer extends BaseEntity<Integer> {
    private String name;

    @OneToMany(mappedBy = "costumer", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @Size(min = 1, message = "You must have at least 1 address")
    private List<Address> addresses;

    private String email;
    private String phoneNumber1;
    @Null
    private String phoneNumber2;
}