package com.ucx.training.shop.controller;

import com.ucx.training.shop.entity.Role;
import com.ucx.training.shop.service.RoleService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1/roles")
public class RoleController {
    private RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping
    public Role create(@RequestBody Role role) {
        return roleService.save(role);
    }

    @GetMapping
    public List<Role>  getAllRoles() {
        return roleService.findAll();
    }
}
