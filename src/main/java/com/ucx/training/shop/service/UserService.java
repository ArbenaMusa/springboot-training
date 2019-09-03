package com.ucx.training.shop.service;

import com.ucx.training.shop.entity.Role;
import com.ucx.training.shop.entity.User;
import com.ucx.training.shop.exception.NotFoundException;
import com.ucx.training.shop.repository.UserRepository;
import com.ucx.training.shop.util.EntityUtil;
import org.springframework.stereotype.Service;

import javax.persistence.Tuple;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class UserService extends BaseService<User, Integer> {

    private UserRepository userRepository;
    private RoleService roleService;

    public UserService(UserRepository userRepository, RoleService roleService) {
        this.userRepository = userRepository;
        this.roleService = roleService;
    }

    public User findByEmail(String email) throws NotFoundException {
        if (email == null) {
            throw new IllegalArgumentException("Name must not be null!");
        }
        User foundUser = userRepository.findByEmail(email);
        if (foundUser == null) {
            throw new NotFoundException("User not found.");
        }
        return foundUser;
    }

    public List<Map<String, Object>> getPermissionsOfUser(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("The given id is null");
        }
        Role foundRole = roleService.findById(id);
        if (foundRole == null) {
            throw new IllegalArgumentException("There isn't a role with the given id");
        }

        List<Tuple> result = userRepository.readAllByRole(id);
        List<Map<String, Object>> convertedList = new ArrayList<>();
        result.forEach(tuple -> convertedList.add(EntityUtil.toMap(tuple)));

        return convertedList;
    }
}
