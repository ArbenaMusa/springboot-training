package com.ucx.training.shop.repository;

import com.ucx.training.shop.entity.Invoice;
import com.ucx.training.shop.entity.LineItem;
import com.ucx.training.shop.entity.Product;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LineItemRepository extends BaseRepository<LineItem,Integer> {
    List<LineItem> findAllByProduct(Product product);
    List<LineItem> findAllByProductAndQuantity(Product product, Integer quantity);
    List<LineItem> findAllByInvoice(Invoice invoice);
}
