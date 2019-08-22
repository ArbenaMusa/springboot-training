package com.ucx.training.shop.controller;

import com.ucx.training.shop.entity.Platform;
import com.ucx.training.shop.exception.NotFoundException;
import com.ucx.training.shop.exception.ResponseException;
import com.ucx.training.shop.service.PlatformService;
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

    private PlatformService platformService;

    public PlatformController(PlatformService platformService) {
        this.platformService = platformService;
    }

    @PostMapping
    public Platform create(@RequestBody Platform platform) throws ResponseException {
        try {
            Platform savedPlatform = platformService.save(platform);
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
            platformService.update(platform, id);
            responseMap.put("id", id);
        } catch (NotFoundException | IllegalArgumentException e) {
            throw new ResponseException(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            throw new ResponseException(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return responseMap;
    }

    @GetMapping("{platformId}")
    public Platform getById(@PathVariable Integer platformId) {
        return platformService.findById(platformId);
    }

    @DeleteMapping("{id}")
    public void remove(@PathVariable Integer id) throws ResponseException {
        try {
            platformService.remove(id);
        } catch (NotFoundException | IllegalArgumentException e) {
            throw new ResponseException(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            throw new ResponseException(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public List<Platform> findAllSorted(@RequestParam(required = false, defaultValue = "ASC") String direction, @RequestParam(defaultValue = "id") String... properties) throws ResponseException {
        try {
            return platformService.findAllSorted(direction, properties);
        } catch (Exception e) {
            throw new ResponseException(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/paged")
    public Map<String, Object> findAllPaged(@PageableDefault Pageable pageable) throws ResponseException {
        try {
            return platformService.findAllPaged(pageable);
        } catch (Exception e) {
            throw new ResponseException(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
