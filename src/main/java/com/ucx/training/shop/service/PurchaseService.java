package com.ucx.training.shop.service;

import com.ucx.training.shop.dto.CartDTO;
import com.ucx.training.shop.dto.PurchaseDTO;
import com.ucx.training.shop.entity.*;
import com.ucx.training.shop.exception.NotFoundException;
import com.ucx.training.shop.type.RecordStatus;
import com.ucx.training.shop.util.uimapper.AddressMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.transaction.Transactional;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Log4j2
@Service
@Transactional
public class PurchaseService {

    private CartItemService cartItemService;
    private OrderService orderService;
    private ProductService productService;
    private CustomerService customerService;
    private EmailService emailService;
    private AddressService addressService;

    public PurchaseService(CartItemService cartItemService, OrderService orderService, ProductService productService, CustomerService customerService, EmailService emailService, AddressService addressService) {
        this.cartItemService = cartItemService;
        this.orderService = orderService;
        this.productService = productService;
        this.customerService = customerService;
        this.emailService = emailService;
        this.addressService = addressService;
    }

    @org.springframework.transaction.annotation.Transactional
    public Order buy(PurchaseDTO purchaseDTO) throws NotFoundException, MessagingException, IOException {
        if (purchaseDTO == null) throw new IllegalArgumentException("The sent purchase is null");
        if (purchaseDTO.getCustomerId() == null) throw new IllegalArgumentException("You must send a customer id");
        Customer foundCustomer = customerService.findById(purchaseDTO.getCustomerId());
        if (foundCustomer == null) throw new RuntimeException("There isn't a customer with the given id");

        Order order = new Order();
        order.setCustomer(foundCustomer);
        if (purchaseDTO.getTotal() != null) {
            order.setTotal(purchaseDTO.getTotal());
        }
        verifyAddress(purchaseDTO, order);
        Order createdOrder = orderService.save(order);

        List<CartDTO> requestList = purchaseDTO.getCart();
        List<CartItem> cart = this.fillCart(createdOrder, requestList);
        createdOrder.setCart(cart);

        emailService.sendMail(foundCustomer, createdOrder);
        return createdOrder;
    }

    private List<CartItem> fillCart(Order createdOrder, List<CartDTO> requestList) {
        List<CartItem> cartItems = new ArrayList<>();
        for (CartDTO item : requestList) {
            Product foundProduct = productService.findById(item.getProductId());
            if (foundProduct == null) {
                throw new RuntimeException("Given id does not exist");
            }
            CartItem createdCartItem = cartItemService.create(foundProduct, item.getQuantity(), createdOrder);
            cartItems.add(createdCartItem);
        }
        return cartItems;
    }

    private void verifyAddress(PurchaseDTO purchaseDTO, Order order) {
        if (purchaseDTO.getAddress() != null) {
            Address address = AddressMapper.mapToAddress(purchaseDTO.getAddress());
            if (address.getId() != null) {
                Address foundAddress = addressService.findById(address.getId());
                if (foundAddress == null) {
                    throw new RuntimeException("The given id for the address is invalid");
                } else {
                    order.setAddress(foundAddress);
                }
            } else {
                order.setAddress(address);
            }
        }
    }
}
