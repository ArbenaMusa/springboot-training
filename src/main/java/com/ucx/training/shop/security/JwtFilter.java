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
    private final static String TOKEN_ENDPOINT = "/api/auth";

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String header = request.getHeader(JwtConstants.HEADER_KEY);

        if (request.getRequestURI().startsWith(TOKEN_ENDPOINT)) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        if (header == null || !header.startsWith(JwtConstants.TOKEN_PREFIX)) {
            throw new ServletException("Missing or invalid authorization header");
        }

        String token = header.substring(ACCESS_TOKEN_INDEX);
        try {
            Claims claims = JwtUtil.parse(token);
            request.setAttribute("claims", claims);
        } catch (Exception e) {
            throw new ServletException(e.getMessage());
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }


}
