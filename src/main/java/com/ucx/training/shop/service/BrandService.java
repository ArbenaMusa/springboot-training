package com.ucx.training.shop.service;

import com.ucx.training.shop.entity.Brand;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class BrandService extends BaseService<Brand,Integer>{
}
