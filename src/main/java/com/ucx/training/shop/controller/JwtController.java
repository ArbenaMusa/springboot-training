package com.ucx.training.shop.controller;

import com.ucx.training.shop.dto.CredentialDTO;
import com.ucx.training.shop.entity.User;
import com.ucx.training.shop.exception.NotFoundException;
import com.ucx.training.shop.service.UserService;
import com.ucx.training.shop.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Base64;
import java.util.HashMap;
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

        //TODO: findCustomerByEmail(email)
        User foundUser = userService.findByEmail(email);

        CredentialDTO credentialDTO = new CredentialDTO(foundUser.getEmail(), foundUser.getPassword());

        //TODO: hash(password).equals(foundUser.getPassword())

        if (!password.equals(credentialDTO.getPassword())) {
            throw new RuntimeException("Invalid login, please check your email and password");
        }

        User user = new User();
        user.setAccessToken(JwtUtil.getAccessToken(email));
        user.setRefreshToken(JwtUtil.getRefreshToken(email));
        foundUser.setAccessToken(JwtUtil.getAccessToken(email));
        foundUser.setRefreshToken(JwtUtil.getRefreshToken(email));
        userService.update(user, foundUser.getId());


        Map<String, String> responseMap = new HashMap<>();

        responseMap.put("accessToken", foundUser.getAccessToken());
        responseMap.put("refreshToken", foundUser.getRefreshToken());
        return responseMap;
    }
}
