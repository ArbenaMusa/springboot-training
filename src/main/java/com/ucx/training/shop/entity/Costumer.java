package com.ucx.training.shop.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Costumer extends BaseEntity<Integer> {
    private String name;


@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
@Size(min = 1, message = "You must have at least 1 address")
    private List<Address> addresses;
}