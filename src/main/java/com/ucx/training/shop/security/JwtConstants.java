package com.ucx.training.shop.security;

public class JwtConstants {
    public static final String SECRET = "SecretKeyToGenerateJWT";
    public static final long EXPIRATION_TIME = 864_000_000;
    public static final String HEADER_KEY = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";
}
