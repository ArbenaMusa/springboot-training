package com.ucx.training.shop.service;

import com.ucx.training.shop.entity.User;
import com.ucx.training.shop.exception.NotFoundException;
import com.ucx.training.shop.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Service
public class AuthenticationService {

    @Autowired
    private UserService userService;

    //LOGIN
    public Map<String, String> login(String email, String password) throws NotFoundException {
        if (email == null || password == null) {
            throw new IllegalArgumentException("One of the arguments is null!");
        }

        User foundUser = userService.findByEmail(email);

        String encodedPassword = Base64.getEncoder().encodeToString(password.getBytes());

        if (!encodedPassword.equals(foundUser.getPassword())) {
            throw new RuntimeException("The password is incorrect!");
        }

        Map<String, String> resultUser = new HashMap<>();
        resultUser.put("userId", foundUser.getId().toString());
        /*resultUser.put("role", foundUser.getRole().getName());*/
        resultUser.put("accessToken", JwtUtil.getAccessToken(foundUser));
        resultUser.put("refreshToken", JwtUtil.getRefreshToken(foundUser));

        return resultUser;
    }

    //REGISTER

    //FORGOT-PASSWORD

    //NEW-PASSWORD
}
