package com.ucx.training.shop.repository;

import com.ucx.training.shop.entity.Category;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends BaseRepository<Category,Integer> {
}
