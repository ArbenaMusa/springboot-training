package com.ucx.training.shop.controller;

import com.ucx.training.shop.dto.BrandDTO;
import com.ucx.training.shop.entity.Role;
import com.ucx.training.shop.exception.ResponseException;
import com.ucx.training.shop.service.RoleService;
import com.ucx.training.shop.util.PaginationUtil;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

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


}
