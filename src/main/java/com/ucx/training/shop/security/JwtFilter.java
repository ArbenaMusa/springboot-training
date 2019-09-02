package com.ucx.training.shop.security;

import com.ucx.training.shop.util.JwtUtil;
import io.jsonwebtoken.Claims;
import lombok.extern.log4j.Log4j2;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Log4j2
public class JwtFilter extends GenericFilter {

    private static final Integer ACCESS_TOKEN_INDEX = 7;
    private final static String AUTH_ENDPOINT = "/api/v1/auth";
    private final static String PRODUCTS_ENDPOINT = "/api/v1/products";
    private final static String PLATFORMS_ENDPOINT = "/api/v1/platforms";
    private final static String BRANDS_ENDPOINT = "/api/v1/brands";

    //TODO: Begin index - [api/v1/], end index -[/]

    // If request is of type "GET", "POST", "DELETE", "UPDATE" filter of some type.

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String header = request.getHeader(JwtConstants.HEADER_KEY);

        if (request.getRequestURI().startsWith(AUTH_ENDPOINT)) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        if (request.getRequestURI().startsWith(PRODUCTS_ENDPOINT) && request.getMethod().equalsIgnoreCase("GET")) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        if (request.getRequestURI().startsWith(PLATFORMS_ENDPOINT) && request.getMethod().equalsIgnoreCase("GET")) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        if (request.getRequestURI().startsWith(BRANDS_ENDPOINT) && request.getMethod().equalsIgnoreCase("GET")) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        if (header == null || !header.startsWith(JwtConstants.TOKEN_PREFIX)) {
            throw new ServletException("Missing or invalid authorization header");
        }

        String token = header.substring(ACCESS_TOKEN_INDEX);
        //TODO: Check if token has expired.
        try {
            Claims claims = JwtUtil.parse(token);
            log.info("CLAIMSSSS: " + claims.getSubject());
            request.setAttribute("claims", claims);
        } catch (Exception e) {
            throw new ServletException(e.getMessage());
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }


}
