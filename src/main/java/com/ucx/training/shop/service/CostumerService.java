package com.ucx.training.shop.service;

import com.ucx.training.shop.dto.AddressDTO;
import com.ucx.training.shop.entity.Address;
import com.ucx.training.shop.entity.Costumer;
import com.ucx.training.shop.exception.NotFoundException;
import com.ucx.training.shop.repository.CostumerRepository;
import com.ucx.training.shop.util.AddressUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.transaction.Transactional;
import java.util.Iterator;
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

    public void updateWithAddresses(Costumer t, Integer u) throws NotFoundException {
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
            previousAddressList.stream().forEach((e) -> {
                Iterator<Address> iterator = receivedAddressList.iterator();
                while (iterator.hasNext()) {
                    Address i = iterator.next();
                    try {
                        if (e.getId().equals(i.getId())) {
                            BeanUtils.copyProperties(i, e, BaseService.<Address>getNullPropertyNames(i));
                            iterator.remove();
                        }
                    } catch (Exception m) {
                        //exception presumably nullPointerException was suppressed
                    }
                }
            });
        }
        receivedAddressList.forEach((e) -> {
            e.setCostumer(foundT);
            addressService.save(e);
        });
        t.setAddresses(null);
        BeanUtils.copyProperties(t, foundT, BaseService.<Costumer>getNullPropertyNames(t));
    }

    public Costumer updateCostumerWithAddress(Costumer updatedCostumer, Integer costumerId) throws NotFoundException {
        Assert.isTrue(updatedCostumer != null, "One of the arguments is invalid!");
        Costumer foundCostumer = findById(costumerId);
        Assert.isTrue(foundCostumer != null, "Entity not found!");
        //if (foundCostumer == null) throw new NotFoundException("Entity not found");

        List<Address> addresses = updatedCostumer.getAddresses();
        for (Address address : addresses) {
            if (address.getId() == null) {
                address.setCostumer(foundCostumer);
                addressService.save(address);
            }else{
                Address foundAddress = addressService.findById(address.getId());
                if (foundAddress == null) {
                    throw new NotFoundException("Address with the given id does not exist!");
                }
                addressService.update(address, foundAddress.getId());
            }
        }
        updatedCostumer.setAddresses(null);
        return update(updatedCostumer, costumerId);
    }
}
