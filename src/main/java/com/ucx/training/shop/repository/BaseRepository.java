package com.ucx.training.shop.repository;

import com.ucx.training.shop.entity.BaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BaseRepository<T extends BaseEntity<U>,U> extends JpaRepository<T, U> {

}
