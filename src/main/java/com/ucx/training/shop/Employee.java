package com.ucx.training.shop;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Employee {

    @Id //Indicates that this field is a Primary Key.
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Id generation strategy
    private Integer id;
    private String name;
    private String address;
//    private List<String> tasks;

}
