package com.ucx.training.shop.security;

import com.ucx.training.shop.service.UserService;
import com.ucx.training.shop.util.JwtUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
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
    private final static String STRIPE_ENDPOINT = "/api/v1/payment";
    private final Integer ADMIN_ROLE_ID = 1;
    private final String ENDPOINT_PREFIX = "api/v1/";
    //TODO: Create ENDPOINT enum, iterate through a loop of these.

    @Autowired
    private UserService userService;

    public JwtFilter(UserService userService) {
        this.userService = userService;
    }


    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String header = request.getHeader(JwtConstants.HEADER_KEY);

        String requestURI = request.getRequestURI();
        String httpMethod = request.getMethod().toUpperCase();

        /*switch (requestURI) {
            case AUTH_ENDPOINT:
            case STRIPE_ENDPOINT:
                filterChain.doFilter(servletRequest, servletResponse);
                return;
            case PRODUCTS_ENDPOINT:
            case PLATFORMS_ENDPOINT:
            case BRANDS_ENDPOINT:
                if (Arrays.asList("GET", "OPTIONS").contains(httpMethod)) {
                    filterChain.doFilter(servletRequest, servletResponse);
                }
                return;
        }*/

         List<String> allMethodsAllowed = Arrays.asList(AUTH_ENDPOINT, STRIPE_ENDPOINT);
         List<String> getOptionsAllowed = Arrays.asList(PRODUCTS_ENDPOINT, PLATFORMS_ENDPOINT, BRANDS_ENDPOINT);

        for (String endpoint : allMethodsAllowed) {
            if (requestURI.startsWith(endpoint)) {
                filterChain.doFilter(servletRequest, servletResponse);
                return;
            }
        }

        for (String endpoint : getOptionsAllowed) {
            if (requestURI.startsWith(endpoint) && Arrays.asList("GET", "OPTIONS").contains(httpMethod)) {
                filterChain.doFilter(servletRequest, servletResponse);
                return;
            }
        }

        /*if (requestURI.startsWith(AUTH_ENDPOINT)) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        if (requestURI.startsWith(STRIPE_ENDPOINT)) {
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
        }*/

        if (header == null || !header.startsWith(JwtConstants.TOKEN_PREFIX)) {
            throw new ServletException("Missing or invalid authorization header");
        }

        String token = header.substring(ACCESS_TOKEN_INDEX);
        Claims claims = null;
        try {
            claims = JwtUtil.parse(token);
        } catch (ExpiredJwtException e) {
            log.debug(e.getMessage(), e);
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Something went wrong, please login again");
            return;
        }

        Integer roleId = (Integer) claims.get("roleId");
        if (roleId.equals(ADMIN_ROLE_ID)) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        final String MODULE = extractModule(requestURI);

        List<Map<String, Object>> filteredList = this.getFilteredList(httpMethod, MODULE, roleId);
        if (!filteredList.isEmpty()) {
            request.setAttribute("claims", claims);
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "You do not have authorization to access the requested route");
        }
    }

    private String extractModule(String requestURI) {
        String fromModuleIndex = requestURI.substring(requestURI.lastIndexOf(ENDPOINT_PREFIX) + ENDPOINT_PREFIX.length());
        int lastIndexOfSlash = fromModuleIndex.indexOf("/");
        final String MODULE;
        if (lastIndexOfSlash != -1) {
            MODULE = fromModuleIndex.substring(0, lastIndexOfSlash);
        } else {
            MODULE = fromModuleIndex;
        }
        return MODULE;
    }

    private List<Map<String, Object>> getFilteredList(String httpMethod, String MODULE, Integer roleId) {
        List<Map<String, Object>> resultList = userService.getPermissionsOfUser(roleId);
        return resultList.stream()
                .filter(e -> {
                    final boolean isTheSameAsHTTPMethod = e.get("action").toString().equalsIgnoreCase(httpMethod);
                    final boolean isTheSameModule = e.get("module").toString().equalsIgnoreCase(MODULE);
                    return isTheSameAsHTTPMethod &&
                            isTheSameModule;
                })
                .collect(Collectors.toList());
    }


}
