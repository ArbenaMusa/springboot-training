package com.ucx.training.shop.util;

import com.ucx.training.shop.security.JwtConstants;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

public class JwtUtil {

    public static String getAccessToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .claim("roles", "user")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + JwtConstants.EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, JwtConstants.SECRET)
                .compact();
    }

    public static Boolean applyJwtFilter(String applyJwtFilter) {
        return Boolean.valueOf(applyJwtFilter);
    }
}
