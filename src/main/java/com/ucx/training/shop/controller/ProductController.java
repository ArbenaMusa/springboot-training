package com.ucx.training.shop.controller;

import com.ucx.training.shop.entity.LineItem;
import com.ucx.training.shop.entity.Product;
import com.ucx.training.shop.service.LineItemService;
import com.ucx.training.shop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("shop/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping
    public Product create(@RequestBody Product product) {
        return productService.save(product);
    }

    @GetMapping
    public List<Product> findAll() {
        return productService.findAll();
    }

    @GetMapping("{id}")
    public Product findById(@PathVariable Integer id) {
        return productService.findById(id);
    }

    @PutMapping("{id}")
    public Product update(@RequestBody Product product) {
        return productService.update(product);
    }

    @DeleteMapping("{id}")
    public void remove(@PathVariable Integer id) {
        productService.remove(id);
    }
}
