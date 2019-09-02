package com.ucx.training.shop.repository;

import com.ucx.training.shop.entity.Product;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ProductRepository extends BaseRepository<Product,Integer> {
    @Query(value = "SELECT * from product where record_status = 'ACTIVE' ", nativeQuery = true)
    public List<Product>  findAllActive();
    Product findByName(String name);
    List<Product> findAllByUnitPrice(BigDecimal unitPrice);
}
