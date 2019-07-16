package com.ucx.training.shop.controller;

import com.ucx.training.shop.dto.ProductDTO;
import com.ucx.training.shop.entity.Costumer;
import com.ucx.training.shop.entity.Product;
import com.ucx.training.shop.service.ProductService;
import com.ucx.training.shop.util.FileUploadUtil;
import com.ucx.training.shop.util.ProductUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("products")
public class ProductController extends BaseController<Product, Integer> {

    @Value("${file.upload}")
    private String uploadDirectoryName;
    private ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    ProductUtil productUtil = new ProductUtil();

    @GetMapping("image/{fileName}")
    public ResponseEntity<InputStreamResource> getImage(@PathVariable String fileName) throws IOException {
        String filePathAsString = System.getProperty("user.dir") + uploadDirectoryName + fileName;
        return ResponseEntity
                .ok()
                .contentType(FileUploadUtil.getContentType(fileName))
                .body(new InputStreamResource(new FileInputStream(filePathAsString)));
    }

    @GetMapping("/all")
    public List<ProductDTO> findAllSorted1(@RequestParam(required = false, defaultValue = "ASC") String direction, @RequestParam(defaultValue = "id") String... properties) {
        List<Product> products = productService.findAllSorted(direction, properties);
        List<ProductDTO> productDTOList = productUtil.getProducts(products);
        return productDTOList;
    }

    @GetMapping("/pages")
    public List<ProductDTO> findAllPaged1(@RequestParam int pageNumber, @RequestParam int pageSize) {
        Page<Product> productPage = productService.findAllPaged(pageNumber, pageSize);
        List<Product> products = productPage.getContent();
        List<ProductDTO> productDTOList = productUtil.getProducts(products);
        return productDTOList;
    }

    @GetMapping("product/{id}")
    public ProductDTO findByID(@PathVariable Integer id) {
        Product product = productService.findById(id);
        return productUtil.getProduct(product);
    }


}
