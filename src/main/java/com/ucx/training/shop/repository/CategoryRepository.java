package com.ucx.training.shop.repository;

import com.ucx.training.shop.entity.Platform;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends BaseRepository<Platform, Integer> {
}
