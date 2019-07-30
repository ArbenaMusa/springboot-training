package com.ucx.training.shop.controller;

import com.ucx.training.shop.dto.DTOEntity;
import com.ucx.training.shop.dto.ProductDTO;
import com.ucx.training.shop.entity.Product;
import com.ucx.training.shop.exception.NotFoundException;
import com.ucx.training.shop.exception.ResponseException;
import com.ucx.training.shop.service.ProductService;
import com.ucx.training.shop.util.FileUploadUtil;
import com.ucx.training.shop.util.uimapper.DTOMapper;
import com.ucx.training.shop.util.uimapper.ProductMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

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
    public ProductDTO create(@RequestBody Product product) throws ResponseException {
        Product createdProduct = null;
        try {
            createdProduct = productService.createProductWithCategoryAndBrand(product);
        } catch (IllegalArgumentException e) {
            throw new ResponseException(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            throw new ResponseException(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return ProductMapper.getProduct(createdProduct);
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
            return DTOMapper.converToDTOList(products,new ProductDTO());
        } catch (Exception e) {
            throw new ResponseException(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/paged")
    public List<ProductDTO> findAllPaged(@RequestParam int pageNumber, @RequestParam int pageSize) throws ResponseException {
        try {
            Page<Product> productPage = productService.findAllPaged(pageNumber, pageSize);
            List<Product> products = productPage.getContent();
            List<ProductDTO> productDTOList = ProductMapper.getProducts(products);
            return productDTOList;
        } catch (Exception e) {
            throw new ResponseException(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("{id}")
    public ProductDTO findByID(@PathVariable Integer id) throws ResponseException {
        try {
            Product product = productService.findById(id);
            return ProductMapper.getProduct(product);
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
