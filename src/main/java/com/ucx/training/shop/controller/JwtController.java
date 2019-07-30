package com.ucx.training.shop.controller;

import com.ucx.training.shop.dto.CredentialDTO;
import com.ucx.training.shop.entity.Customer;
import com.ucx.training.shop.entity.User;
import com.ucx.training.shop.exception.NotFoundException;
import com.ucx.training.shop.security.JwtConstants;
import com.ucx.training.shop.service.CustomerService;
import com.ucx.training.shop.service.UserService;
import com.ucx.training.shop.util.JwtUtil;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("tokens")

public class JwtController {

    private UserService userService;
    private JwtController(UserService userService)
    {
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

        foundUser.setAccessToken(JwtUtil.getAccessToken(email));
        foundUser.setRefreshToken(JwtUtil.getRefreshToken(email));
        userService.update(foundUser, foundUser.getId());


        Map<String, String> responseMap = new HashMap<>();

        responseMap.put("accessToken", foundUser.getAccessToken());
        responseMap.put("refreshToken", foundUser.getRefreshToken());
        return responseMap;
    }
}
