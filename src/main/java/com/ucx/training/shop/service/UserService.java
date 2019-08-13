package com.ucx.training.shop.service;

import com.ucx.training.shop.entity.User;
import com.ucx.training.shop.exception.NotFoundException;
import com.ucx.training.shop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class UserService extends BaseService<User, Integer> {

    @Autowired
    private UserRepository userRepository;

    public User findByEmail(String email) throws NotFoundException {
        if (email == null) {
            throw new IllegalArgumentException("Email cannot be null");
        }
        User foundUser = userRepository.findByEmail(email);
        if (foundUser == null) {
            throw new NotFoundException("There isn't a User with the given email");
        }

        return foundUser;
    }

    public User login(String email, String password) throws NotFoundException {
        if (email == null || password == null) {
            throw new IllegalArgumentException("Arguments cannot be null");
        }
        User foundUser = findByEmail(email);
        if (!foundUser.getPassword().equals(password)) {
            throw new IllegalArgumentException("The given password is incorrect");
        }
        return foundUser;
    }
}
