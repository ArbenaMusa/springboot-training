package com.ucx.training.shop.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "`user`")
public class User extends BaseEntity<Integer> {

    @Column(unique = true)
    private String email;
    @Column(length = 60)
    @NotNull
    @Size(min = 4, message = "Password must contain at least 4 characters")
    private String password;
    private Boolean isVerified = Boolean.FALSE;

    @ManyToOne(fetch = FetchType.LAZY)
    private Role role;
}
