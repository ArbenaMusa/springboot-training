package com.ucx.training.shop.controller;

import com.ucx.training.shop.dto.CredentialDTO;
import com.ucx.training.shop.security.JwtConstants;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("token")
public class JwtController {

    @PostMapping
    public Map<String, String> getToken(@RequestBody CredentialDTO credentialDTO) {
        Assert.isTrue(credentialDTO.getEmail() != null, "Email must not be null");
        Assert.isTrue(credentialDTO.getPassword() != null, "Password must not be null");

        String email = credentialDTO.getEmail();
        String password = credentialDTO.getPassword();

        CredentialDTO foundCredentialDTO = new CredentialDTO("user@shop.com", "user");
        Assert.isTrue(foundCredentialDTO.getEmail().equals(email), "Invalid email");
        Assert.isTrue(foundCredentialDTO.getPassword().equals(password), "Invalid password");

        String jwtToken = Jwts.builder()
                .setSubject(email)
                .claim("roles", "user")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + JwtConstants.EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, JwtConstants.SECRET)
                .compact();

        Map<String, String> responseMap = new HashMap<>();
        responseMap.put("token", jwtToken);
        return responseMap;
    }
}
