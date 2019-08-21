package com.ucx.training.shop.repository;

import com.ucx.training.shop.entity.Role;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends BaseRepository<Role, Integer> {

    Role findRoleByName(String name);
}
