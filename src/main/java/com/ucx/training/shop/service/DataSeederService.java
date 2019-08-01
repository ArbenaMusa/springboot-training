package com.ucx.training.shop.service;

import com.ucx.training.shop.entity.Permission;
import com.ucx.training.shop.entity.Role;
import com.ucx.training.shop.repository.PermissionRepository;
import com.ucx.training.shop.repository.RoleRepository;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Log4j2
@Component
public class DataSeederService implements CommandLineRunner {


    @Autowired
    RoleRepository roleRepository;
    @Autowired
    PermissionRepository permissionRepository;


    @Override
    public void run(String... args) throws Exception {
        List<Role> rolesList = new ArrayList();
        List<Permission> permissionList = new ArrayList<>();


        Role adminRole = new Role();
        Role customerRole = new Role();
        Role guestRole = new Role();


        adminRole.setName("Admin");
        adminRole.setRoleDescription("Admin is the user with all the privileges of management");
        customerRole.setName("User");
        customerRole.setRoleDescription("Customer are registered users who buy items");
        guestRole.setName("Guest");
        guestRole.setRoleDescription("Guest are visitors who dont have an account and" +
                " want to buy goods");

        rolesList.addAll(Arrays.asList(adminRole, customerRole, guestRole));


        Permission createUser = new Permission();
        createUser.setAction("createUser");
        createUser.setModule("Accounts");
        Permission editUser = new Permission();
        editUser.setAction("editUser");
        editUser.setModule("Accounts");
        Permission viewUser = new Permission();
        viewUser.setModule("Accounts");
        viewUser.setAction("viewUser");
        Permission editProfile = new Permission();
        editProfile.setAction("editProfile");
        editProfile.setModule("Accounts");
        Permission viewProfile = new Permission();
        viewProfile.setAction("viewProfile");
        viewProfile.setModule("Accounts");
        Permission suspendUser = new Permission();
        suspendUser.setModule("Accounts");
        suspendUser.setAction("suspendUser");
        Permission deleteAccount = new Permission();
        deleteAccount.setModule("Accounts");
        deleteAccount.setAction("deleteAccount");

        permissionList.addAll(Arrays.asList(editProfile, suspendUser, deleteAccount,
                viewProfile, createUser, editUser, viewUser));

        permissionRepository.deleteAll();
        roleRepository.deleteAll();



        List lsta = Arrays.asList(adminRole);
        permissionList.stream().forEach((e) -> {
            e.setRoles(lsta);
        });

        permissionRepository.saveAll(permissionList);
        adminRole.setPermissionList(permissionList);
        roleRepository.saveAll(rolesList);

    }
}
