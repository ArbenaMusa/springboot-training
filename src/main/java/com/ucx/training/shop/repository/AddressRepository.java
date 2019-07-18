package com.ucx.training.shop.repository;

import com.ucx.training.shop.entity.Address;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends BaseRepository<Address, Integer> {
    Address findAllById(Integer addressId);

}
