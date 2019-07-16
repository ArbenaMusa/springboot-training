package com.ucx.training.shop.repository;

import com.ucx.training.shop.entity.Product;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ProductRepository extends BaseRepository<Product,Integer> {
    List<Product> findAllByName(String name);
    List<Product> findAllByInStock(Boolean inStock);
    List<Product> findAllByUnitPrice(BigDecimal unitPrice);
}
