package com.ucx.training.shop.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "permission")
public class Permission extends BaseEntity<Integer> {

    private String module;
    private String action;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "permission_role", joinColumns = {@JoinColumn(name = "permission_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id")}
    )
    private List<Role> roles;
}
