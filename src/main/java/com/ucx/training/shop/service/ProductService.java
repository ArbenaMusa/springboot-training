package com.ucx.training.shop.service;

import com.ucx.training.shop.entity.FileUpload;
import com.ucx.training.shop.entity.Product;
import com.ucx.training.shop.exception.NotFoundException;
import com.ucx.training.shop.repository.ProductRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional
@Log4j2
public class ProductService extends BaseService<Product,Integer> {

    @Autowired
    private ProductRepository productRepository;

    public List<Product> findAllByName(String name) {
        if (name == null) {
            throw new IllegalArgumentException("Null argument provided!");
        }

        return productRepository.findAllByName(name);
    }

    public List<Product> findAllByUnitPrice(BigDecimal unitPrice) {
        if (unitPrice == null) {
            throw new IllegalArgumentException("Null argument provided!");
        }

        return productRepository.findAllByUnitPrice(unitPrice);
    }

}
