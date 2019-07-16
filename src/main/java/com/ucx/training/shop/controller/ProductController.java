package com.ucx.training.shop.controller;

import com.ucx.training.shop.entity.Product;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("products")
public class ProductController extends BaseController<Product,Integer>{

}
