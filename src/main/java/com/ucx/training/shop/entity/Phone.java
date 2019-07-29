package com.ucx.training.shop.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Phone extends BaseEntity<Integer>{

    private String phoneNumber;

    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private Costumer costumer;
}
