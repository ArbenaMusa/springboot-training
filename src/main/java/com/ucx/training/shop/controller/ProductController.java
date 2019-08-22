package com.ucx.training.shop.controller;

import com.ucx.training.shop.dto.DTOEntity;
import com.ucx.training.shop.dto.ProductDTO;
import com.ucx.training.shop.entity.Product;
import com.ucx.training.shop.exception.NotFoundException;
import com.ucx.training.shop.exception.ResponseException;
import com.ucx.training.shop.service.ProductService;
import com.ucx.training.shop.util.FileUploadUtil;
import com.ucx.training.shop.util.uimapper.DTOMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;

@Log4j2
@RestController
@RequestMapping("v1/products")
public class ProductController {

    @Value("${file.upload}")
    private String uploadDirectoryName;
    private ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public DTOEntity create(@RequestBody Product product) throws ResponseException {
        Product createdProduct = null;
        try {
            createdProduct = productService.createProductWithPlatformAndBrand(product);
        } catch (IllegalArgumentException e) {
            throw new ResponseException(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            throw new ResponseException(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return DTOMapper.convertToDto(createdProduct, ProductDTO.class);
    }

    @PutMapping("{productId}")
    public void update(@RequestBody Product product, @PathVariable Integer productId) throws ResponseException {
        try {
            productService.update(product, productId);
        } catch (Exception e) {
            throw new ResponseException(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("image/{fileName}")
    public ResponseEntity<InputStreamResource> getImage(@PathVariable String fileName) throws ResponseException {
        String filePathAsString = System.getProperty("user.dir") + uploadDirectoryName + fileName;
        try {
            return ResponseEntity
                    .ok()
                    .contentType(FileUploadUtil.getContentType(fileName))
                    .body(new InputStreamResource(new FileInputStream(filePathAsString)));
        } catch (FileNotFoundException e) {
            throw new ResponseException(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public List<DTOEntity> findAllSorted(@RequestParam(required = false, defaultValue = "ASC") String direction, @RequestParam(defaultValue = "id") String... properties) throws ResponseException {
        try {
            List<Product> products = productService.findAllSorted(direction, properties);
            return DTOMapper.converToDTOList(products, ProductDTO.class);
        } catch (Exception e) {
            throw new ResponseException(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/paged")
    public Map<String, Object> findAllPaged(@PageableDefault Pageable pageable) throws ResponseException {
        try {
           return productService.findAllPaged(pageable);
        } catch (Exception e) {
            throw new ResponseException(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("{id}")
    public DTOEntity findByID(@PathVariable Integer id) throws ResponseException {
        try {
            Product product = productService.findById(id);
            return DTOMapper.convertToDto(product, ProductDTO.class);
        } catch (IllegalArgumentException e) {
            throw new ResponseException(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            throw new ResponseException(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("{id}")
    public void remove(@PathVariable Integer id) throws ResponseException {
        try {
            productService.remove(id);
        } catch (NotFoundException | IllegalArgumentException e) {
            throw new ResponseException(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            throw new ResponseException(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
