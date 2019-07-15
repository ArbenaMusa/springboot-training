package com.ucx.training.shop.service;

import com.ucx.training.shop.entity.Product;
import com.ucx.training.shop.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional
public class ProductService extends BaseService<Product,Integer> {

    @Autowired
    private ProductRepository productRepository;



    public List<Product> findAllByName(String name) {
        if (name == null) {
            throw new IllegalArgumentException("Invalid argument: " + name);
        }

        return productRepository.findAllByName(name);
    }

    public List<Product> findAllByInStock(Boolean inStock) {
        if (inStock == null) {
            throw new IllegalArgumentException("Invalid argument: " + inStock);
        }

        return productRepository.findAllByInStock(inStock);
    }

    public List<Product> findAllByUnitPrice(BigDecimal unitPrice) {
        if (unitPrice == null) {
            throw new IllegalArgumentException("Invalid argument: " + unitPrice);
        }

        return productRepository.findAllByUnitPrice(unitPrice);
    }
}
