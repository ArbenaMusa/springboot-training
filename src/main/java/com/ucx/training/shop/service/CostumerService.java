package com.ucx.training.shop.service;

import com.ucx.training.shop.entity.Address;
import com.ucx.training.shop.entity.Costumer;
import com.ucx.training.shop.repository.CostumerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class CostumerService extends BaseService<Costumer, Integer> {

    @Autowired
    private CostumerRepository costumerRepository;

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
}
