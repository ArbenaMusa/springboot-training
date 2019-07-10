package com.ucx.training.shop.repository;

import com.ucx.training.shop.entity.Product;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends BaseRepository<Product,Integer> {
}
