package com.ucx.training.shop.service;

import com.ucx.training.shop.entity.Phone;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class PhoneService extends BaseService<Phone, Integer> {
}
