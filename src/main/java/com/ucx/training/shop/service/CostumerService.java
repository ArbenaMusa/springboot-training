package com.ucx.training.shop.service;

import com.ucx.training.shop.dto.AddressDTO;
import com.ucx.training.shop.entity.Address;
import com.ucx.training.shop.entity.Costumer;
import com.ucx.training.shop.exception.NotFoundException;
import com.ucx.training.shop.repository.AddressRepository;
import com.ucx.training.shop.repository.CostumerRepository;
import com.ucx.training.shop.util.AddressUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class CostumerService extends BaseService<Costumer, Integer> {

    @Autowired
    private CostumerRepository costumerRepository;
    @Autowired
    AddressService addressService;

    @Override
    public Costumer save(Costumer costumer) {
        costumer.getAddresses().forEach(e -> e.setCostumer(costumer));
        return super.save(costumer);
    }

    List<Costumer> findAllByName(String name) {
        if (name == null) {
            throw new IllegalArgumentException("Invalid argument: " + name);
        }
        return costumerRepository.findAllByName(name);
    }

    public AddressDTO updateAddress(Address address, Integer addressId) throws NotFoundException {
        if (address == null) {
            throw new IllegalArgumentException("Invalid address argument: " + address);
        } else if (addressId == null) {
            throw new IllegalArgumentException("Invalid addressId argument: " + addressId);
        }

        return AddressUtil.getAddress(addressService.update(address, addressId));

    }


    @Override
    public Costumer update(Costumer t, Integer u) throws NotFoundException {
        if (t == null) {
            throw new IllegalArgumentException(String.format("One of the arguments is invalid: %s", t));
        }
        Costumer foundT = findById(u);
        if (foundT == null) {
            throw new NotFoundException("Entity not found");
        }

        List<Address> previousAddressList = foundT.getAddresses();
        List<Address> receivedAddressList = t.getAddresses();
        if (receivedAddressList != null) {
            previousAddressList.stream().forEach(
                    (e) -> {
                        receivedAddressList.stream().forEach((i) -> {
                            if (e.getId().equals(i.getId())) {
                                BeanUtils.copyProperties(i, e, BaseService.<Address>getNullPropertyNames(i));
                                receivedAddressList.remove(i);

                            }

                        });

                    }
            );

            receivedAddressList.stream().forEach(previousAddressList::add);
        }

        BeanUtils.copyProperties(t, foundT, BaseService.<Costumer>getNullPropertyNames(t));
        return foundT;
    }
}
