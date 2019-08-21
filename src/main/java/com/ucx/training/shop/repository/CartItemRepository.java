package com.ucx.training.shop.repository;

import com.ucx.training.shop.entity.Order;
import com.ucx.training.shop.entity.CartItem;
import com.ucx.training.shop.entity.Product;
import org.springframework.stereotype.Repository;
import com.ucx.training.shop.type.RecordStatus;

import java.util.List;

@Repository
public interface CartItemRepository extends BaseRepository<CartItem,Integer> {
    List<CartItem> findAllByOrderAndRecordStatus(Order order, RecordStatus recordStatus);
    List<CartItem> findAllByOrderIdAndRecordStatus(Integer invoiceId, RecordStatus recordStatus);

}
