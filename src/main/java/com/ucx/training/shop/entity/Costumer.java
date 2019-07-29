package com.ucx.training.shop.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Costumer extends BaseEntity<Integer> {
    private String name;

    @OneToMany(mappedBy = "costumer", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    @Size(min = 1, message = "You must have at least 1 address")
    private List<Address> addresses;

    @Column(unique = true)
    private String email;
    @OneToMany(mappedBy = "costumer",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    Set<Phone> phoneNumbers;
}
