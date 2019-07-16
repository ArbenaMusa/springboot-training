package com.ucx.training.shop.controller;

import com.ucx.training.shop.entity.FileUpload;
import com.ucx.training.shop.service.FileUploadService;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@RestController
@RequestMapping("/upload")
@Log4j2
public class FileUploadController {

    private FileUploadService fileUploadService;

    public FileUploadController(FileUploadService fileUploadService) {
        this.fileUploadService = fileUploadService;
    }

    @PostMapping
    @ResponseBody
    public void uploadFile(@RequestParam("files") MultipartFile file) {
        try {
            FileUpload uploadedFile = fileUploadService.uploadFile(file);
            fileUploadService.save(uploadedFile);
        } catch (IOException ex) {
            log.info(ex.getMessage());
        }
    }
}


