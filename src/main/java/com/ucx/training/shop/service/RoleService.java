package com.ucx.training.shop.service;

import com.ucx.training.shop.entity.Role;
import com.ucx.training.shop.exception.NotFoundException;
import com.ucx.training.shop.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@Transactional
public class RoleService extends BaseService<Role, Integer> {

    @Autowired
    private RoleRepository roleRepository;

    public Role findRoleByName(String name) throws NotFoundException {
        if (name == null) {
            throw new IllegalArgumentException("Name cannot be null!");
        }
        Role foundRole = roleRepository.findRoleByName(name);
        if (foundRole == null) {
            throw new NotFoundException("This role does not exist");
        }
        return foundRole;
    }





}
