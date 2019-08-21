package com.ucx.training.shop.repository;

import com.ucx.training.shop.entity.CartItem;
import com.ucx.training.shop.entity.Category;
import com.ucx.training.shop.entity.Order;
import com.ucx.training.shop.type.RecordStatus;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends BaseRepository<Category, Integer> {

    @Query(value = "SELECT * from category where record_status = 'ACTIVE' ", nativeQuery = true)
    public List<Category>  findAllActive();
}
