package com.ucx.training.shop.controller;

import com.ucx.training.shop.exception.NotFoundException;
import com.ucx.training.shop.entity.BaseEntity;
import com.ucx.training.shop.exception.ResponseException;
import com.ucx.training.shop.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

public class BaseController <T extends BaseEntity<U>,U>{
    @Autowired
    protected BaseService<T,U> baseService;

    @PostMapping
    public T create(@RequestBody T t){
        return baseService.save(t);
    }

    @PutMapping("{id}")
    public T update(@RequestBody T t, @PathVariable U id) throws NotFoundException{
        return baseService.update(t, id);
    }

    @DeleteMapping("{id}")
    public void remove(@PathVariable U id) throws NotFoundException {
        baseService.remove(id);
    }

    @GetMapping
    public List<T> findAllSorted(@RequestParam(required = false, defaultValue = "ASC") String direction, @RequestParam(defaultValue = "id") String ... properties) {
        return baseService.findAllSorted(direction, properties);
    }

    @GetMapping("{id}")
    public T findById(@PathVariable U id) { return baseService.findById(id);}

    @GetMapping("/paged")
    public Map<String, Object> findAllPaged(@PageableDefault Pageable pageable) throws ResponseException {
        try {
            return baseService.findAllPaged(pageable);
        } catch (Exception e) {
            throw new ResponseException(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
