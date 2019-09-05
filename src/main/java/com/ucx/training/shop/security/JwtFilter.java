package com.ucx.training.shop.security;

import com.ucx.training.shop.service.UserService;
import com.ucx.training.shop.util.JwtUtil;
import io.jsonwebtoken.Claims;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Log4j2
public class JwtFilter extends GenericFilter {

    private static final Integer ACCESS_TOKEN_INDEX = 7;
    private final static String AUTH_ENDPOINT = "/api/v1/auth";
    private final static String PRODUCTS_ENDPOINT = "/api/v1/products";
    private final static String PLATFORMS_ENDPOINT = "/api/v1/platforms";
    private final static String BRANDS_ENDPOINT = "/api/v1/brands";

    @Autowired
    private UserService userService;

    public JwtFilter(UserService userService) {
        this.userService = userService;
    }


    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String header = request.getHeader(JwtConstants.HEADER_KEY);

        String requestURI = request.getRequestURI();
        String httpMethod = request.getMethod();

        if (requestURI.startsWith(AUTH_ENDPOINT)) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        if (requestURI.startsWith(PRODUCTS_ENDPOINT) &&
                ((httpMethod.equalsIgnoreCase("GET"))
                 || (httpMethod.equalsIgnoreCase("OPTIONS")))) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        if (requestURI.startsWith(PLATFORMS_ENDPOINT) && httpMethod.equalsIgnoreCase("GET")) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        if (requestURI.startsWith(BRANDS_ENDPOINT) && httpMethod.equalsIgnoreCase("GET")) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        if (header == null || !header.startsWith(JwtConstants.TOKEN_PREFIX)) {
            throw new ServletException("Missing or invalid authorization header");
        }

        String token = header.substring(ACCESS_TOKEN_INDEX);
        //TODO: Check if token has expired.
        Claims claims = JwtUtil.parse(token);

        Integer roleId = (Integer) claims.get("roleId");
        if (roleId == 1) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        String urlPrefix = "api/v1/";
        String fromModuleIndex = requestURI.substring(requestURI.lastIndexOf(urlPrefix) + urlPrefix.length());
        int lastIndexOfSlash = fromModuleIndex.indexOf("/");
        final String MODULE;
        if (lastIndexOfSlash != -1) {
            MODULE = fromModuleIndex.substring(0, lastIndexOfSlash);
        } else {
            MODULE = fromModuleIndex;
        }
        log.info("MODULE SUBSTRING: " + MODULE);

        List<Map<String, Object>> resultList = userService.getPermissionsOfUser(roleId);
        List<Map<String, Object>> filteredList = resultList.stream()
                .filter(e -> e.get("action").toString().equalsIgnoreCase(httpMethod) &&
                        e.get("module").toString().equalsIgnoreCase(MODULE))
                .collect(Collectors.toList());
        log.info("\nFILTERED LIST: \n" + filteredList);
        if (!filteredList.isEmpty()) {
            request.setAttribute("claims", claims);
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            throw new RuntimeException("You do not have authorization to access the requested route");
        }
    }


}
