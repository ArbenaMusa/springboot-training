package com.ucx.training.shop.controller;

import com.ucx.training.shop.service.StripePayment;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("v1/payment")
public class PaymentController {

    private StripePayment stripePayment;

    public PaymentController(StripePayment stripePayment){
        this.stripePayment = stripePayment;
    }

    @PostMapping("{customerId}")
    public String createCustomer(@PathVariable int customerId){
        this.stripePayment.createCustomer(customerId);
        return stripePayment.getTokenId();
    }

    @PostMapping("/charge")
    public void chargeCreditCard(@RequestParam("amount") BigDecimal amount) {
        this.stripePayment.chargeCreditCard(amount);
    }

    @GetMapping
    public String getTokenId(){
        return stripePayment.getTokenId();
    }


}
