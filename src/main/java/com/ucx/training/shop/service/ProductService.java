package com.ucx.training.shop.service;

import com.ucx.training.shop.entity.Product;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class ProductService extends BaseService<Product,Integer> {
}
