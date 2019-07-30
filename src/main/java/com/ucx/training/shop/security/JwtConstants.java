package com.ucx.training.shop.security;

public class JwtConstants {
    public static final String SECRET_ACCESS = "SecretKeyToGenerateJWTAccessToken";
    public static final String SECRET_REFRESH = "SecretKeyToGenerateJWTRefreshToken";
    public static final long EXPIRATION_TIME_ACCESS = 600_000;
    public static final long EXPIRATION_TIME_REFRESH = 3_600_000;
    public static final String HEADER_KEY = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";
}
