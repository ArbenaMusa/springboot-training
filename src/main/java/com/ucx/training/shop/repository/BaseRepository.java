package com.ucx.training.shop.repository;

import com.ucx.training.shop.entity.BaseEntity;
import com.ucx.training.shop.entity.Category;
import com.ucx.training.shop.type.RecordStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BaseRepository<T extends BaseEntity<U>, U> extends JpaRepository<T, U> {


}
