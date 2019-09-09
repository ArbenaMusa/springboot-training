package com.ucx.training.shop.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ucx.training.shop.dto.DTOEntity;
import com.ucx.training.shop.dto.ProductDTO;
import com.ucx.training.shop.entity.Product;
import com.ucx.training.shop.exception.NotFoundException;
import com.ucx.training.shop.exception.ResponseException;
import com.ucx.training.shop.service.ProductService;
import com.ucx.training.shop.type.RecordStatus;
import com.ucx.training.shop.util.FileUploadUtil;
import com.ucx.training.shop.util.PaginationUtil;
import com.ucx.training.shop.util.uimapper.DTOMapper;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Log4j2
@RestController
@RequestMapping("v1/products")
public class ProductController {

    @Value("${file.upload}")
    private String uploadDirectoryName;
    private ProductService productService;
    private ModelMapper modelMapper;
    private ObjectMapper objectMapper = new ObjectMapper();

    public ProductController(ProductService productService, ModelMapper modelMapper) {
        this.productService = productService;
        this.modelMapper = modelMapper;
    }

    @PostMapping
    public DTOEntity create(@RequestBody Product product) throws ResponseException {
        Product createdProduct = null;
        try {
            createdProduct = productService.create(product);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            throw new ResponseException(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseException(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return DTOMapper.convertToDto(createdProduct, ProductDTO.class);
    }

    @PutMapping("/{productId}")
    public void update(@RequestBody Product product, @PathVariable Integer productId) throws ResponseException {
        try {
            productService.create(product);
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
            List<Product> products = productService.findAllSorted(direction, properties)
                    .stream()
                    .filter(product -> product.getRecordStatus() == RecordStatus.ACTIVE)
                    .collect(Collectors.toList());
            return DTOMapper.converToDTOList(products, ProductDTO.class);
        } catch (Exception e) {
            throw new ResponseException(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/paged")
    public Map<String, Object> findAllPaged(@PageableDefault Pageable pageable) throws ResponseException {
        try {
            return PaginationUtil.getPage(productService.findPaged(pageable), ProductDTO.class);
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

    @GetMapping("/name/{name}")
    public Integer findByName(@PathVariable String name)
    {
        return this.productService.findByName(name).getId();
    }

    @GetMapping("/allActive")
    public Map<String, Object> findAllActive(@PageableDefault Pageable pageable) throws ResponseException{
        return PaginationUtil.getPage(productService.findAllActive(pageable),null);
    }

    @GetMapping("/filter")
    public List<DTOEntity> findAllFilters(@RequestParam(required = false, value = "platformId") Integer platformId, @RequestParam(required = false, value = "brandId") Integer brandId, @RequestParam(required = false, value = "priceOrder") String priceDirection, @RequestParam(required = false, value = "min") BigDecimal min, @RequestParam(required = false, value = "max") BigDecimal max) throws ResponseException {
        List<Product> foundProducts = null;

        try{
            if(platformId != null && brandId != null){
                foundProducts = productService.findAllByPlatformAndBrand(min, max, platformId, brandId, priceDirection);
            }
            else if(platformId != null){
                foundProducts = productService.findAllByPlatform(min, max, platformId, priceDirection);
            }
            else if(brandId != null){
                foundProducts = productService.findAllByBrand(min, max, brandId, priceDirection);
            }
            else if(platformId == null && brandId == null && priceDirection != null){
                foundProducts = productService.findAllSorted(priceDirection, "unitPrice");
            }
            else {
                foundProducts = productService.findAllProductsPrice(min, max, priceDirection);
            }
            return DTOMapper.converToDTOList(foundProducts, ProductDTO.class);
        } catch (Exception e) {
            throw new ResponseException(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getLowestPrice")
    public Number getLowestPrices(){
        return productService.getLowestPrice();
    }

    @GetMapping("/getHighestPrice")
    public Number getHighestPrice(){
        return productService.getHighestPrice();
    }

    @GetMapping("/nameSearch/")
    public Map<String, Object> findAllByNameContaining(@PageableDefault Pageable pageable, @RequestParam("name") String name) throws  ResponseException{
        try{

            return PaginationUtil.getPage(this.productService.findAllByNameContaining(pageable, name), ProductDTO.class);
        }catch (Exception e){
            throw new ResponseException(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
