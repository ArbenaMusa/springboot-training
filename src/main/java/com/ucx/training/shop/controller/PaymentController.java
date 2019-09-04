package com.ucx.training.shop.controller;

import com.ucx.training.shop.entity.Customer;
import com.ucx.training.shop.service.CustomerService;
import com.ucx.training.shop.service.StripePayment;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/payment")
public class PaymentController {

    private StripePayment stripePayment;
    private CustomerService customerService;

    public PaymentController(StripePayment stripePayment,CustomerService customerService){
        this.stripePayment = stripePayment;
        this.customerService = customerService;
    }

    @PostMapping("{customerId}")
    public String createCustomer(@PathVariable int customerId){
        Customer customer = this.customerService.findById(customerId);
        this.stripePayment.createCustomer(customer);
        return stripePayment.getTokenId();
    }

    @PostMapping("/charge")
    public void chargeCreditCard() {
        this.stripePayment.chargeCreditCard();
    }

    @GetMapping
    public String getTokenId(){
        return stripePayment.getTokenId();
    }


}
