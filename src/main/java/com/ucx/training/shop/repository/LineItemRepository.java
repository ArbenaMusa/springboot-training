package com.ucx.training.shop.repository;

import com.ucx.training.shop.entity.LineItem;
import org.springframework.stereotype.Repository;

@Repository
public interface LineItemRepository extends BaseRepository<LineItem,Integer> {
}
