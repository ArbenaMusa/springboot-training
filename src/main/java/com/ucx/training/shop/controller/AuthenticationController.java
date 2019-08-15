package com.ucx.training.shop.controller;

import com.ucx.training.shop.dto.CredentialDTO;
import com.ucx.training.shop.dto.CustomerDTO;
import com.ucx.training.shop.dto.DTOEntity;
import com.ucx.training.shop.entity.Customer;
import com.ucx.training.shop.entity.User;
import com.ucx.training.shop.exception.NotFoundException;
import com.ucx.training.shop.exception.ResponseException;
import com.ucx.training.shop.service.AuthenticationService;
import com.ucx.training.shop.service.CustomerService;
import com.ucx.training.shop.service.UserService;
import com.ucx.training.shop.util.JwtUtil;
import com.ucx.training.shop.util.uimapper.DTOMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("v1/auth")

public class AuthenticationController {

    private UserService userService;
    private AuthenticationService authenticationService;
    private CustomerService customerService;

    public AuthenticationController(UserService userService, AuthenticationService authenticationService, CustomerService customerService) {
        this.userService = userService;
        this.authenticationService = authenticationService;
        this.customerService = customerService;
    }

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody Map<String, String> credentials) throws ResponseException {
        try {
            String email = credentials.get("email");
            String password = credentials.get("password");
            return authenticationService.login(email, password);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseException(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/register")
    public DTOEntity register(@RequestBody Customer customer) throws ResponseException {
        try {
            Customer createdCustomer = customerService.save(customer);
            return new DTOMapper().convertToDto(customer,new CustomerDTO());
        } catch (IllegalArgumentException e) {
            throw new ResponseException(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            throw new ResponseException(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping
    public Map<String, String> getToken(@RequestBody Map<String, String> credentials) throws NotFoundException {

        String decodedCredentials = new String(Base64.getDecoder().decode(credentials.get("creds").getBytes()));
        String[] parts = decodedCredentials.split(";");
        String email = parts[0];
        String password = parts[1];

        //TODO: findCustomerByEmail(email)
        User foundUser = userService.findByEmail(email);

        //TODO: hash(password).equals(foundUser.getPassword())

        if (!password.equals(foundUser.getPassword())) {
            throw new RuntimeException("Invalid login, please check your email and password");
        }

        User user = new User();
        user.setAccessToken(JwtUtil.getAccessToken(email));
        user.setRefreshToken(JwtUtil.getRefreshToken(email));
        foundUser.setAccessToken(JwtUtil.getAccessToken(email));
        foundUser.setRefreshToken(JwtUtil.getRefreshToken(email));
        userService.update(user, foundUser.getId());

        Map<String, String> responseMap = new HashMap<>();

        responseMap.put("accessToken", foundUser.getAccessToken());
        responseMap.put("refreshToken", foundUser.getRefreshToken());
        return responseMap;
    }
}
