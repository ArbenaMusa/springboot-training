package com.ucx.training.shop.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Costumer extends BaseEntity<Integer> {
    private String name;

    @OneToMany(mappedBy = "costumer", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @Size(min = 1, message = "You must have at least 1 address")
    private List<Address> addresses;

    private String email;
    private String phoneNumber1;
    private String phoneNumber2;
}
