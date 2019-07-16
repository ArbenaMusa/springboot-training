package com.ucx.training.shop.controller;

import com.ucx.training.shop.entity.Costumer;
import com.ucx.training.shop.service.CostumerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("costumers")
public class CostumerController extends BaseController<Costumer, Integer> {

}
