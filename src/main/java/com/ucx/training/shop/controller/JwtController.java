package com.ucx.training.shop.controller;

import com.ucx.training.shop.entity.User;
import com.ucx.training.shop.exception.NotFoundException;
import com.ucx.training.shop.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Base64;
import java.util.Map;

@RestController
@RequestMapping("tokens")

public class JwtController {

    private UserService userService;

    public JwtController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public Map<String, String> getToken(@RequestBody Map<String, String> credentials) throws NotFoundException {

        String decodedCredentials = new String(Base64.getDecoder().decode(credentials.get("creds").getBytes()));
        String[] parts = decodedCredentials.split(";");
        String email = parts[0];
        String password = parts[1];

        User foundUser = userService.authenticateUser(email, password);

        User user =userService.setUserToken(email, foundUser.getId());

        return userService.getTokenMap(user);
    }
}
