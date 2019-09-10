package com.ucx.training.shop.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Brand extends BaseEntity<Integer> {
    private String name;
    @Column(length = 1000)
    private String brandLink;
    @Column(length = 1000)
    private String imageLink;
}
