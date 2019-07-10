package com.ucx.training.shop.controller;

import com.ucx.training.shop.entity.LineItem;
import com.ucx.training.shop.service.LineItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("shop/lineitems")
public class LineItemController {

    @Autowired
    private LineItemService lineItemService;

    @PostMapping
    public LineItem create(@RequestBody LineItem lineItem) {
        return lineItemService.save(lineItem);
    }

    @GetMapping
    public List<LineItem> findAll() {
        return lineItemService.findAll();
    }

    @GetMapping("{id}")
    public LineItem findById(@PathVariable Integer id) {
        return lineItemService.findById(id);
    }

    @PutMapping("{id}")
    public LineItem update(@RequestBody LineItem lineItem) {
        return lineItemService.update(lineItem);
    }

    @DeleteMapping("{id}")
    public void remove(@PathVariable Integer id) {
        lineItemService.remove(id);
    }
}
