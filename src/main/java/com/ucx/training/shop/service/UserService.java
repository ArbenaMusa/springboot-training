package com.ucx.training.shop.service;

import com.ucx.training.shop.dto.CredentialDTO;
import com.ucx.training.shop.entity.User;
import com.ucx.training.shop.exception.NotFoundException;
import com.ucx.training.shop.repository.UserRepository;
import com.ucx.training.shop.util.JwtUtil;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.Map;

@Service
@Transactional
public class UserService extends BaseService<User, Integer> {
    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findByEmail(String email) {
        if (email == null) {
            throw new IllegalArgumentException("Name must not be null!");
        }
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new RuntimeException("User not found.");
        }
        return userRepository.findByEmail(email);
    }

    public User authenticateUser(String email, String password) throws NotFoundException
    {
        //TODO: findCustomerByEmail(email)
        User foundUser = findByEmail(email);
        CredentialDTO credentialDTO = new CredentialDTO(foundUser.getEmail(), foundUser.getPassword());

        //TODO: hash(password).equals(foundUser.getPassword())
        if (!password.equals(credentialDTO.getPassword())) {
            throw new RuntimeException("Invalid login, please check your email and password");
        }
        return foundUser;
    }
    public User setUserTokens(String email, Integer id) throws NotFoundException
    {
        User newUser = new User();
        setUserAccessToken(email, id);
        setUserRefreshToken(email, id);
        update(newUser, id);
        return findById(id);
    }
    public void setUserAccessToken(String email, Integer id) throws NotFoundException
    {
        User newUser = new User();
        newUser.setAccessToken(JwtUtil.getAccessToken(email));
        update(newUser, id);
    }
    public void setUserRefreshToken(String email, Integer id) throws NotFoundException
    {
        User newUser = new User();
        newUser.setRefreshToken(JwtUtil.getRefreshToken(email));
        update(newUser, id);
    }
    public void setUserNullToken(Integer id) throws NotFoundException
    {
        User newUser = new User();
        newUser.setAccessToken(null);
        newUser.setRefreshToken(null);
        update(newUser, id);
    }
    public Map<String,String> getTokenMap(User user)
    {
        Map<String, String> responseMap = new HashMap<>();
        responseMap.put("accessToken", user.getAccessToken());
        responseMap.put("refreshToken", user.getRefreshToken());
        return responseMap;
    }
}
