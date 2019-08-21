package com.ucx.training.shop.repository;

import com.ucx.training.shop.entity.Customer;
import com.ucx.training.shop.entity.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends BaseRepository<User, Integer> {
    User findByEmail(String email);
}
