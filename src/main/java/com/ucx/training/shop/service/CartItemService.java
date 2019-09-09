package com.ucx.training.shop.service;

import com.ucx.training.shop.entity.Order;
import com.ucx.training.shop.entity.CartItem;
import com.ucx.training.shop.entity.Product;
import com.ucx.training.shop.exception.NotFoundException;
import com.ucx.training.shop.repository.CartItemRepository;
import com.ucx.training.shop.type.RecordStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class CartItemService extends BaseService<CartItem, Integer> {

    @Autowired
    private CartItemRepository cartItemRepository;

    public CartItem create(Product product, Integer quantity, Order order) {
        if (product == null) {
            throw new IllegalArgumentException("Cannot create LineItem, Product is missing");
        }
        if (quantity == null || quantity <= 0) {
            throw new IllegalArgumentException("Quantity cannot be less than 1");
        }
        if (order == null) {
            throw new IllegalArgumentException("Order should have already been created");
        }

        CartItem cartItem = new CartItem();
        cartItem.setProduct(product);
        cartItem.setQuantity(quantity);
        cartItem.setOrder(order);
        return save(cartItem);
    }

    public List<CartItem> findAllByOrderId(Integer orderId) throws NotFoundException {
        if (orderId == null) {
            throw new IllegalArgumentException("Invoice ID cannot be null .-");
        }
        List<CartItem> cartItemList = cartItemRepository.findAllByOrderIdAndRecordStatus(orderId, RecordStatus.ACTIVE);
        if (cartItemList.size() == 0) {
            throw new NotFoundException("There are no cart items with the given invoice id!" + orderId);
        }
        return cartItemList;
    }


}
