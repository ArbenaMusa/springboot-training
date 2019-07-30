package com.ucx.training.shop.service;

import com.ucx.training.shop.entity.User;
import com.ucx.training.shop.exception.NotFoundException;
import com.ucx.training.shop.repository.UserRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class UserService extends BaseService<User, Integer> {

    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
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


}
