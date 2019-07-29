package com.ucx.training.shop.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Permission extends BaseEntity<Integer>{

    private String moduleName;
    private String actionName;

    @ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinTable(name = "permission_role",joinColumns = @JoinColumn(name = "permission_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Role> roles;

}
