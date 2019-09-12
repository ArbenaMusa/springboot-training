package com.ucx.training.shop.security;

public class JwtConstants {
    public static final String SECRET_ACCESS = "SecretKeyToGenerateJWTAccessToken";
    public static final String SECRET_REFRESH = "SecretKeyToGenerateJWTRefreshToken";
    public static final long EXPIRATION_TIME_ACCESS = 200_000_000;
    public static final long EXPIRATION_TIME_REFRESH = 600_000_000;
    public static final String HEADER_KEY = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";
}
