package com.ucx.training.shop.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Category extends BaseEntity<Integer> {

    private String name;
}
