package com.ucx.training.shop.service;

import com.ucx.training.shop.entity.Category;
import com.ucx.training.shop.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService extends BaseService<Category, Integer> {

    @Autowired
    public CategoryRepository categoryRepository;

    public List<Category> findAllActive() {
        return categoryRepository.findAllActive();
    }
}
