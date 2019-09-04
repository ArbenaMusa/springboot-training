package com.ucx.training.shop.service;

import com.stripe.Stripe;
import com.stripe.exception.*;
import com.stripe.model.Charge;
import com.ucx.training.shop.entity.Customer;
import com.ucx.training.shop.repository.PaymentInterface;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class StripePayment implements PaymentInterface {

    private static final String TEST_STRIPE_SECRET_KEY = "sk_test_F80HwLcBteuczUKFSiC2KEKw00TvPxtOTe";
    Map<String,String> token_id = new HashMap<>();
    public StripePayment(){
        Stripe.apiKey = TEST_STRIPE_SECRET_KEY;
    }

    public Map<String,String> createCustomer(Customer customer){

        Map<String,Object> customerParams = new HashMap<>();
        customerParams.put("description", customer.getName());
        customerParams.put("name", customer.getName());
        customerParams.put("email",customer.getUser().getEmail());

        Map<String,Object> cardParams = new HashMap<>();
        cardParams.put("source","tok_visa");

        try{
            com.stripe.model.Customer stripeCustomer = com.stripe.model.Customer.create(customerParams);
            token_id.put("customer_id",stripeCustomer.getId());
            com.stripe.model.Customer foundStripeCustomer = com.stripe.model.Customer.retrieve(token_id.get("customer_id"));
            foundStripeCustomer.getSources().create(cardParams);
            System.out.println(stripeCustomer);
        }catch(CardException e){
            e.printStackTrace();
        }catch(RateLimitException e){
            e.printStackTrace();
        }catch (InvalidRequestException e){
            e.printStackTrace();
        }catch (AuthenticationException e){
            e.printStackTrace();
        }catch (StripeException e){
            e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }

        return token_id;
    }

    public String getTokenId(){
        return this.token_id.get("customer_id");
    }



    public void chargeCreditCard(){

        Map<String,Object> chargeParams = new HashMap<>();
        chargeParams.put("amount",1000);
        chargeParams.put("currency","usd");
        chargeParams.put("description","One time charge");
        chargeParams.put("customer", this.getTokenId());

        try{
            Charge charge = Charge.create(chargeParams);
            System.out.println(charge);
        }catch (CardException e){
            e.printStackTrace();
        }catch (RateLimitException e){
            e.printStackTrace();
        }catch (InvalidRequestException e){
            e.printStackTrace();
        }catch (AuthenticationException e){
            e.printStackTrace();
        }catch (ApiConnectionException e){
            e.printStackTrace();
        }catch (StripeException e){
            e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
