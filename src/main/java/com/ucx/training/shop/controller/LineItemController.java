package com.ucx.training.shop.controller;

import com.ucx.training.shop.entity.LineItem;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("lineitems")
public class LineItemController extends BaseController<LineItem, Integer>{

}
