package com.ucx.training.shop.repository;

import com.ucx.training.shop.entity.Address;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends BaseRepository<Address, Integer> {
    Address findAllById(Integer addressId);

    List<Address> findAllByCustomer_IdOrderByIdDesc(Integer id);

}
