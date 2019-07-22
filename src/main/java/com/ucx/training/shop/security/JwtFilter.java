package com.ucx.training.shop.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.extern.log4j.Log4j2;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Log4j2
public class JwtFilter extends GenericFilter {

    private final static String TOKEN_ENDPOINT = "/shop/tokens";

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String header = request.getHeader(JwtConstants.HEADER_KEY);

        if (request.getRequestURI().startsWith(TOKEN_ENDPOINT)) {
            log.info("REQUEST: " + request.getRequestURI());
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }
        if (header == null || !header.startsWith(JwtConstants.TOKEN_PREFIX)) {
            throw new ServletException("Missing or invalid authorization header");
        }

        String token = header.substring(7);
        try {
            Claims claims = Jwts
                    .parser()
                    .setSigningKey(JwtConstants.SECRET)
                    .parseClaimsJws(token)
                    .getBody();
            request.setAttribute("claims", claims);
        } catch (Exception e) {
            throw new ServletException(e.getMessage());
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }
}
