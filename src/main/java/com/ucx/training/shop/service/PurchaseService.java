package com.ucx.training.shop.service;

import com.ucx.training.shop.entity.Customer;
import com.ucx.training.shop.entity.Order;
import com.ucx.training.shop.entity.CartItem;
import com.ucx.training.shop.entity.Product;
import com.ucx.training.shop.exception.NotFoundException;
import com.ucx.training.shop.type.RecordStatus;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.transaction.Transactional;
import java.io.IOException;
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

    public PurchaseService(CartItemService cartItemService, OrderService orderService, ProductService productService, CustomerService customerService, EmailService emailService) {
        this.cartItemService = cartItemService;
        this.orderService = orderService;
        this.productService = productService;
        this.customerService = customerService;
        this.emailService = emailService;
    }

    public Integer addToCart(Integer productId, Integer quantity, Integer invoiceId) throws NotFoundException {
        Integer newInvoiceId;
        Product foundProduct = productService.findById(productId);
        if (foundProduct == null) throw new NotFoundException("Product could not be found!");
        if (foundProduct.getRecordStatus() == RecordStatus.INACTIVE)
            throw new IllegalArgumentException("Product is deleted! " + foundProduct);
        if (quantity == null) throw new IllegalArgumentException("No quantity provided!");
        if (foundProduct.getInStock() < quantity) throw new NotFoundException("Out of stock!");

        if (invoiceId == null) {
            Order order = new Order();
            Order createdOrder = orderService.save(order);
            cartItemService.create(foundProduct, quantity, createdOrder);
            newInvoiceId = createdOrder.getId();
        } else {
            Order foundOrder = orderService.findById(invoiceId);
            if (foundOrder == null) throw new NotFoundException("No such Invoice found");
            if (foundOrder.getRecordStatus() == RecordStatus.INACTIVE)
                throw new IllegalArgumentException("Invoice is deleted! " + foundOrder);
            cartItemService.create(foundProduct, quantity, foundOrder);
            newInvoiceId = invoiceId;
        }

        return newInvoiceId;
    }

    public Order buy(Integer costumerId, Integer invoiceId) throws NotFoundException, MessagingException, IOException {
        Order foundOrder = orderService.findById(invoiceId);
        if (foundOrder == null) throw new NotFoundException("No such Invoice found" + invoiceId);
        if (foundOrder.getRecordStatus() == RecordStatus.INACTIVE)
            throw new IllegalArgumentException("Invoice is deleted: " + foundOrder);
        Customer foundCustomer = customerService.findById(costumerId);
        if (foundCustomer == null) throw new NotFoundException("No such Costumer found" + costumerId);
        if (foundCustomer.getRecordStatus() == RecordStatus.INACTIVE)
            throw new IllegalArgumentException("Costumer is deleted: " + foundCustomer);
        List<CartItem> cartItemList = cartItemService.findAllByOrderAndRecordStatusActive(foundOrder);
        Order generatedOrder = orderService.update(cartItemList, foundCustomer, foundOrder);
        Order printedOrder = orderService.print(generatedOrder.getId());
        reduceStock(cartItemList);

        emailService.sendMail(foundCustomer, generatedOrder);
        return printedOrder;
    }

    //Reduces stock of Product from quantity given
    private void reduceStock(List<CartItem> cartItemList) {
        cartItemList.forEach(lineItem -> {
            Product product = lineItem.getProduct();
            product.setInStock(product.getInStock() - lineItem.getQuantity());
        });
    }

    public CartItem cancelLineItem(Integer lineItemId) throws NotFoundException {
        if (lineItemId == null) {
            throw new IllegalArgumentException("LineItem ID cannot be null!");
        }
        CartItem foundCartItem = cartItemService.findById(lineItemId);
        if (foundCartItem == null) {
            throw new NotFoundException("LineItem does not exist");
        }
        foundCartItem.setRecordStatus(RecordStatus.INACTIVE);
        return foundCartItem;
    }

    public Order cancelPurchase(Integer invoiceId) throws NotFoundException {
        if (invoiceId == null) throw new IllegalArgumentException("Invoice ID cannot be null!");
        Order foundOrder = orderService.findById(invoiceId);
        if (foundOrder == null) throw new NotFoundException("Invoice does not exist" + invoiceId);
        if (!foundOrder.getCart().isEmpty()) {
            foundOrder.getCart().forEach(lineItem -> {
                try {
                    cancelLineItem(lineItem.getId());
                } catch (NotFoundException e) {
                    log.error(e.getMessage());
                }
            });
        }
        foundOrder.setRecordStatus(RecordStatus.INACTIVE);
        return foundOrder;
    }

    public CartItem changeQuantity(CartItem cartItem, Integer lineItemId) throws NotFoundException {
        if (lineItemId == null) {
            throw new IllegalArgumentException("LineItem cannot be null!");
        }
        CartItem foundCartItem = cartItemService.findById(lineItemId);
        if (foundCartItem == null) {
            throw new NotFoundException("Line item does not exist");
        }
        if (foundCartItem.getRecordStatus() == RecordStatus.INACTIVE) {
            throw new RuntimeException("This line item was deleted!");
        }
        if (cartItem.getQuantity().equals(0)) {
            return cancelLineItem(lineItemId);
        }
        foundCartItem.setQuantity(cartItem.getQuantity());
        return foundCartItem;
    }
}
