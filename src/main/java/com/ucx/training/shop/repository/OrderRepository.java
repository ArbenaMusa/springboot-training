package com.ucx.training.shop.repository;

import com.ucx.training.shop.entity.Customer;
import com.ucx.training.shop.entity.Order;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends BaseRepository<Order, Integer> {
    List<Order> findAllByCustomer(Customer customer);
}
