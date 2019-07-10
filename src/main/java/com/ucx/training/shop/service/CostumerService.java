package com.ucx.training.shop.service;

import com.ucx.training.shop.entity.Costumer;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class CostumerService extends BaseService<Costumer, Integer> {
}
