package com.ucx.training.shop.controller;

import com.ucx.training.shop.entity.Product;
import com.ucx.training.shop.util.FileUploadUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileInputStream;
import java.io.IOException;

@RestController
@RequestMapping("products")
public class ProductController extends BaseController<Product, Integer> {

    @Value("${file.upload}")
    private String uploadDirectoryName;

    @GetMapping("image/{fileName}")
    public ResponseEntity<InputStreamResource> getImage(@PathVariable String fileName) throws IOException {
        String filePathAsString = System.getProperty("user.dir") + uploadDirectoryName + fileName;
        return ResponseEntity
                .ok()
                .contentType(FileUploadUtil.getContentType(fileName))
                .body(new InputStreamResource(new FileInputStream(filePathAsString)));
    }

}
