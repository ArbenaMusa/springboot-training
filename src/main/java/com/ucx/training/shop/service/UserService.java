package com.ucx.training.shop.service;

import com.ucx.training.shop.entity.User;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class UserService extends BaseService<User, Integer> {
}
