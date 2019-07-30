package com.ucx.training.shop.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "phone")
public class Phone extends BaseEntity<Integer> {

    @NotNull
    private String phoneNumber;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Customer customer;
}
