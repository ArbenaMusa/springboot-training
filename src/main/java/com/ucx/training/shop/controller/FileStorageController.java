package com.ucx.training.shop.controller;

import com.ucx.training.shop.service.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController("/files")
public class FileStorageController {

    @Autowired
    private FileStorageService storageService;

    @PostMapping
    public void uploadFile(@RequestParam MultipartFile file) throws IOException {
        storageService.uploadFile(file);
    }

}
