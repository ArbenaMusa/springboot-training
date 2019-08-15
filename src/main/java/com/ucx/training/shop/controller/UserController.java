package com.ucx.training.shop.controller;

import com.ucx.training.shop.entity.User;
import com.ucx.training.shop.exception.NotFoundException;
import com.ucx.training.shop.exception.ResponseException;
import com.ucx.training.shop.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1/users")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public User create(@RequestBody User user) throws ResponseException {
        try {
            return userService.save(user);
        } catch (Exception e) {
            throw new ResponseException(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public List<User> findAllSorted(@RequestParam(required = false, defaultValue = "ASC") String direction, @RequestParam(defaultValue = "id") String... properties) throws ResponseException {
        try {
            return userService.findAllSorted(direction, properties);
        } catch (Exception e) {
            throw new ResponseException(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/paged")
    public List<User> findAllPaged(@RequestParam int pageNumber, @RequestParam int pageSize) throws ResponseException {
        try {
            Page<User> userPage = userService.findAllPaged(pageNumber, pageSize);
            return userPage.getContent();
        } catch (Exception e) {
            throw new ResponseException(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("{id}")
    public User findById(@PathVariable Integer id) throws ResponseException {
        try {
            return userService.findById(id);
        } catch (Exception e) {
            throw new ResponseException(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("{id}")
    public User update(@RequestBody User user, @PathVariable Integer id) throws ResponseException {
        try {
            return userService.update(user, id);
        } catch (NotFoundException e) {
            throw new ResponseException(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("{id}")
    public void remove(@PathVariable Integer id) throws ResponseException {
        try {
            userService.remove(id);
        } catch (NotFoundException e) {
            throw new ResponseException(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
