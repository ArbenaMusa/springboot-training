package com.ucx.training.shop.repository;

import com.stripe.model.Order;
import com.ucx.training.shop.entity.Customer;

import java.util.Map;

public interface PaymentInterface {
    Map<String,String> createCustomer(Customer customer);
    void chargeCreditCard();
}
