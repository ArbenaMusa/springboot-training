package com.ucx.training.shop.service;

import com.ucx.training.shop.entity.Category;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class CategoryService extends BaseService<Category,Integer> {
}
