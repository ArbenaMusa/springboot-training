package com.ucx.training.shop.util;

import com.ucx.training.shop.entity.User;
import com.ucx.training.shop.security.JwtConstants;
import io.jsonwebtoken.*;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.DatatypeConverter;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Base64;
import java.util.Date;

import static com.ucx.training.shop.security.JwtConstants.SECRET_ACCESS;
import static com.ucx.training.shop.security.JwtConstants.SECRET_REFRESH;

public class JwtUtil {

    public static String getAccessToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .claim("roles", "user")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + JwtConstants.EXPIRATION_TIME_ACCESS))
                .signWith(SignatureAlgorithm.HS512, SECRET_ACCESS)
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
                .setSigningKey(SECRET_ACCESS)
                .parseClaimsJws(token)
                .getBody();
        return claims;
    }
    public static Claims parseRefresh(String token) {
        Claims claims = Jwts
                .parser()
                .setSigningKey(SECRET_REFRESH)
                .parseClaimsJws(token)
                .getBody();
        return claims;
    }
    public static Boolean checkExpirationAccess(String token, User user)
    {
        try {
            Claims claims = parse(token);
            Date exp = claims.getExpiration();

            Date now = new Date();
            if (now.compareTo(exp) > 0) return true;
            else return false;

        } catch (ExpiredJwtException ex) {
            user.setAccessToken(getAccessToken(user.getEmail()));
        }
        return false;
    }
    public static Boolean checkExpirationRefresh(String token, User user)
    {
        try {
            Claims claims = parseRefresh(token);
            Date exp = claims.getExpiration();

            Date now = new Date();
            if (now.compareTo(exp) > 0) return true;
            else return false;

        } catch (ExpiredJwtException ex) {
            user.setAccessToken(null);
            user.setRefreshToken(null);
        }
        return false;
    }
}
