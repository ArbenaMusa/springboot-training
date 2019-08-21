package com.ucx.training.shop.repository;

import com.ucx.training.shop.entity.Phone;
import org.springframework.stereotype.Repository;

import javax.persistence.Entity;

@Repository
public interface PhoneRepository extends BaseRepository<Phone, Integer> {
}
