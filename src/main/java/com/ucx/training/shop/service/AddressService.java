package com.ucx.training.shop.service;

import com.ucx.training.shop.entity.Address;
import com.ucx.training.shop.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressService extends BaseService<Address,Integer>{

    @Autowired
    private AddressRepository addressRepository;

    public Integer findAllAddressesByCustomerId(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("The given id is null");
        }
        List<Address> queryResult = this.addressRepository.findAllByCustomer_IdOrderByIdDesc(id);
        Address address = queryResult.get(0);
        if (address == null) {
            throw new RuntimeException("The last address is null");
        }
        return address.getId();
    }
}
