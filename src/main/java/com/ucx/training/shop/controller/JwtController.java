package com.ucx.training.shop.controller;

import com.ucx.training.shop.dto.CredentialDTO;
import com.ucx.training.shop.security.JwtConstants;
import com.ucx.training.shop.util.JwtUtil;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("tokens")
public class JwtController {

    @PostMapping
    public Map<String, String> getToken(@RequestBody Map<String, String> credentials) {

        String decodedCredentials = new String(Base64.getDecoder().decode(credentials.get("creds").getBytes()));
        String[] parts = decodedCredentials.split(";");
        String email = parts[0];
        String password = parts[1];

        //TODO: findUserByEmail(email)
        CredentialDTO credentialDTO = new CredentialDTO("user@shop.com", "user");

        //TODO: hash(password).equals(foundUser.getPassword())

        if (!password.equals(credentialDTO.getPassword())) {
            throw new RuntimeException("Invalid login, please check your email and password");
        }

        String accessToken = JwtUtil.getAccessToken(email);

        Map<String, String> responseMap = new HashMap<>();
        responseMap.put("accessToken", accessToken);
        return responseMap;
    }
}
