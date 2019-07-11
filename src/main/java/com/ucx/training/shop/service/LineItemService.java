package com.ucx.training.shop.service;

import com.ucx.training.shop.entity.LineItem;
import com.ucx.training.shop.entity.Product;
import com.ucx.training.shop.repository.LineItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class LineItemService extends BaseService<LineItem,Integer> {

    @Autowired
    private LineItemRepository lineItemRepository;

    public List<LineItem> findAllByProduct(Product product) {
        if (product == null) {
            throw new IllegalArgumentException("Invalid argument: " + product);
        }

        return lineItemRepository.findAllByProduct(product);
    }

    public List<LineItem> findAllByProductAndQuantity(Product product, Integer quantity) {
        if (product == null || quantity == null || quantity <= 0) {
            throw new IllegalArgumentException("Invalid argument: " + product);
        }

        return lineItemRepository.findAllByProductAndQuantity(product, quantity);
    }
}
