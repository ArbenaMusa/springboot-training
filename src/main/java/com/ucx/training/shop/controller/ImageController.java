package com.ucx.training.shop.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@RestController
public class ImageController {
    @Value("${file.upload.client}")
    private String uploadDirectory;

    @GetMapping(value = "/assets/img/{image}", produces = "image/*")
    public byte[] getImage(@PathVariable String image) {
        File file = new File(uploadDirectory + image);
        try {
            return Files.readAllBytes(file.toPath());
        } catch (IOException e) {
            return null;
        }
    }
}
