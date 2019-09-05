package com.ucx.training.shop.repository;

import java.math.BigDecimal;
import java.util.Map;

public interface PaymentInterface {
    Map<String,String> createCustomer(int customerId);
    void chargeCreditCard(BigDecimal amount);
}
