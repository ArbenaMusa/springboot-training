package com.ucx.training.shop.controller;

import com.ucx.training.shop.dto.BrandDTO;
import com.ucx.training.shop.dto.PlatformDTO;
import com.ucx.training.shop.entity.Platform;
import com.ucx.training.shop.exception.NotFoundException;
import com.ucx.training.shop.exception.ResponseException;
import com.ucx.training.shop.service.CategoryService;
import com.ucx.training.shop.util.PaginationUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Log4j2
@RestController
@RequestMapping("v1/categories")
public class PlatformController {

    private CategoryService categoryService;

    public PlatformController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    public Platform create(@RequestBody Platform platform) throws ResponseException {
        try {
            Platform savedPlatform = categoryService.save(platform);
            return savedPlatform;
        } catch (IllegalArgumentException e) {
            throw new ResponseException(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            throw new ResponseException(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("{id}")
    public Map<String, Integer> update(@RequestBody Platform platform, @PathVariable Integer id) throws ResponseException {
        Map<String, Integer> responseMap = new HashMap<>();
        try {
            categoryService.update(platform, id);
            responseMap.put("id", id);
        } catch (NotFoundException | IllegalArgumentException e) {
            throw new ResponseException(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            throw new ResponseException(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return responseMap;
    }

    @GetMapping("{categoryId}")
    public Platform getById(@PathVariable Integer categoryId) {
        return categoryService.findById(categoryId);
    }

    @DeleteMapping("{id}")
    public void remove(@PathVariable Integer id) throws ResponseException {
        try {
            categoryService.remove(id);
        } catch (NotFoundException | IllegalArgumentException e) {
            throw new ResponseException(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            throw new ResponseException(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public List<Platform> findAllSorted(@RequestParam(required = false, defaultValue = "ASC") String direction, @RequestParam(defaultValue = "id") String... properties) throws ResponseException {
        try {
            return categoryService.findAllSorted(direction, properties);
        } catch (Exception e) {
            throw new ResponseException(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/paged")
    public Map<String, Object> findAllPaged(@PageableDefault Pageable pageable) throws ResponseException {
        try {
            return PaginationUtil.getPage(categoryService.findPaged(pageable), PlatformDTO.class);
        } catch (Exception e) {
            throw new ResponseException(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
