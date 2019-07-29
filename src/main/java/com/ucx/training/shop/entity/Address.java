package com.ucx.training.shop.entity;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Address extends BaseEntity<Integer> {
    private String street;
    private Integer zipCode;
    private String city;
    private String country;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "costumer_id")
    private Customer customer;
}
