package com.ucx.training.shop.service;

import com.ucx.training.shop.entity.Customer;
import com.ucx.training.shop.entity.Order;
import com.ucx.training.shop.entity.CartItem;
import com.ucx.training.shop.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional
public class OrderService extends BaseService<Order, Integer> {

    @Autowired
    private OrderRepository orderRepository;

    public Order update(List<CartItem> cartItemList, Customer customer, Order order) {
        if (cartItemList == null || cartItemList.isEmpty()) {
            throw new IllegalArgumentException("Cannot print Invoice, list is missing");
        }
        if (customer == null) {
            throw new IllegalArgumentException("Cannot print Invoice, Costumer is missing");
        }
        if (order == null) {
            throw new IllegalArgumentException("Invoice must not be null");
        }
        BigDecimal total = cartItemList
                .stream()
                .map(e -> e.getProduct().getUnitPrice().multiply(BigDecimal.valueOf(e.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        order.setCart(cartItemList);
        order.setCustomer(customer);
        order.setTotal(total);
        return order;
    }

    public Order print(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("Invalid argument: " + id);
        }
        return findById(id);
    }

    public List<Order> findAllByCostumer(Customer customer) {
        if (customer == null) {
            throw new IllegalArgumentException("Invalid argument: " + customer);
        }

        return orderRepository.findAllByCustomer(customer);
    }




}
