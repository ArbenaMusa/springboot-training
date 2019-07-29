package com.ucx.training.shop.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "role")
public class Role extends BaseEntity<Integer> {

    @Column(unique = true)
    private String name;
    private String roleDescription;

    @ManyToMany(mappedBy = "roles")
    private List<Permission> permissionList;
}
