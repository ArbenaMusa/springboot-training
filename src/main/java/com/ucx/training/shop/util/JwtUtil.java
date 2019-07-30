package com.ucx.training.shop.util;

import com.ucx.training.shop.security.JwtConstants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

public class JwtUtil {

    public static String getAccessToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .claim("roles", "user")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + JwtConstants.EXPIRATION_TIME_ACCESS))
                .signWith(SignatureAlgorithm.HS512, JwtConstants.SECRET_ACCESS)
                .compact();
    }

    public static String getRefreshToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .claim("roles", "user")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + JwtConstants.EXPIRATION_TIME_REFRESH))
                .signWith(SignatureAlgorithm.HS256, JwtConstants.SECRET_REFRESH)
                .compact();
    }

    public static Boolean applyJwtFilter(String applyJwtFilter) {
        return Boolean.valueOf(applyJwtFilter);
    }

    public static Claims parse(String token) {
        Claims claims = Jwts
                .parser()
                .setSigningKey(JwtConstants.SECRET_ACCESS)
                .parseClaimsJws(token)
                .getBody();
        return claims;
    }
}
