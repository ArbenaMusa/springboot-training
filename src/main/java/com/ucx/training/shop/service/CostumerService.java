package com.ucx.training.shop.service;

import com.ucx.training.shop.dto.AddressDTO;
import com.ucx.training.shop.entity.Address;
import com.ucx.training.shop.entity.Costumer;
import com.ucx.training.shop.exception.NotFoundException;
import com.ucx.training.shop.repository.CostumerRepository;
import com.ucx.training.shop.util.AddressUtil;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class CostumerService extends BaseService<Costumer, Integer> {

    private CostumerRepository costumerRepository;
    private AddressService addressService;

    public CostumerService(CostumerRepository costumerRepository, AddressService addressService) {
        this.costumerRepository = costumerRepository;
        this.addressService = addressService;
    }

    @Override
    public Costumer save(Costumer costumer) {
        if (costumer.getAddresses() == null) {
            throw new IllegalArgumentException("You must have at least 1 address");
        }
        costumer.getAddresses().forEach(e -> e.setCostumer(costumer));
        return super.save(costumer);
    }

    public List<Costumer> findAllByName(String name) {
        if (name == null) {
            throw new IllegalArgumentException("Name must not be null!");
        }
        return costumerRepository.findAllByName(name);
    }

    public AddressDTO updateAddress(Address address, Integer addressId) throws NotFoundException {
        if (address == null || addressId == null) {
            throw new IllegalArgumentException("Either address or addressId is null!");
        }
        return AddressUtil.getAddress(addressService.update(address, addressId));
    }

}
