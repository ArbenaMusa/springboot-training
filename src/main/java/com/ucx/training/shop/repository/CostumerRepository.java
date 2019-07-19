package com.ucx.training.shop.repository;

import com.ucx.training.shop.entity.Costumer;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CostumerRepository extends BaseRepository<Costumer,Integer> {
    List<Costumer> findAllByName(String name);
}
