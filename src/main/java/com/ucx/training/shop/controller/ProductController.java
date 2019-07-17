package com.ucx.training.shop.controller;

import com.ucx.training.shop.dto.ProductDTO;
import com.ucx.training.shop.entity.Product;
import com.ucx.training.shop.exception.ResponseException;
import com.ucx.training.shop.service.ProductService;
import com.ucx.training.shop.util.FileUploadUtil;
import com.ucx.training.shop.util.ProductUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("products")
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
            createdProduct = productService.save(product);
        } catch (Exception e) {
            throw new ResponseException(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return ProductUtil.getProduct(createdProduct);
    }

    @GetMapping("image/{fileName}")
    public ResponseEntity<InputStreamResource> getImage(@PathVariable String fileName) throws IOException {
        String filePathAsString = System.getProperty("user.dir") + uploadDirectoryName + fileName;
        return ResponseEntity
                .ok()
                .contentType(FileUploadUtil.getContentType(fileName))
                .body(new InputStreamResource(new FileInputStream(filePathAsString)));
    }

    @GetMapping
    public List<ProductDTO> findAllSorted(@RequestParam(required = false, defaultValue = "ASC") String direction, @RequestParam(defaultValue = "id") String... properties) {
        List<Product> products = productService.findAllSorted(direction, properties);
        List<ProductDTO> productDTOList = ProductUtil.getProducts(products);
        return productDTOList;
    }

    @GetMapping("/paged")
    public List<ProductDTO> findAllPaged(@RequestParam int pageNumber, @RequestParam int pageSize) {
        Page<Product> productPage = productService.findAllPaged(pageNumber, pageSize);
        List<Product> products = productPage.getContent();
        List<ProductDTO> productDTOList = ProductUtil.getProducts(products);
        return productDTOList;
    }

    @GetMapping("{id}")
    public ProductDTO findByID(@PathVariable Integer id) {
        Product product = productService.findById(id);
        return ProductUtil.getProduct(product);
    }

}
