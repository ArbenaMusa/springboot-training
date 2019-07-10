package com.ucx.training.shop.service;

import com.ucx.training.shop.entity.LineItem;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class LineItemService extends BaseService<LineItem,Integer> {
}
